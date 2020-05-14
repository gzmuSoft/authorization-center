package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.handler.*
import cn.edu.gzmu.center.model.BadRequestException
import cn.edu.gzmu.center.model.ForbiddenException
import cn.edu.gzmu.center.model.UnauthorizedException
import cn.edu.gzmu.center.model.extension.oauth
import cn.edu.gzmu.center.model.address.Oauth
import cn.edu.gzmu.center.model.address.Oauth.Companion.OAUTH
import cn.edu.gzmu.center.model.address.Oauth.Companion.SERVER
import com.google.common.net.HttpHeaders
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.core.eventbus.EventBus
import io.vertx.core.http.HttpMethod
import io.vertx.ext.auth.oauth2.OAuth2Auth
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.ext.consul.CheckOptions
import io.vertx.ext.consul.ConsulClient
import io.vertx.ext.consul.ServiceOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.ext.consul.deregisterServiceAwait
import io.vertx.kotlin.ext.consul.registerCheckAwait
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.RuntimeException

/**
 * Web Verticle.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/1 下午6:22
 */
class WebVerticle : CoroutineVerticle() {

  private val log: Logger = LoggerFactory.getLogger(WebVerticle::class.java.name)
  private lateinit var consulClient: ConsulClient
  private lateinit var name: String

  /**
   * Will get config from application.conf and start Web server.
   */
  override suspend fun start() {
    val router = Router.router(vertx)
    router.route().handler(BodyHandler.create())
    router.route().handler(::beforeHandler)
    router.route().handler(
      CorsHandler.create("*")
        .allowCredentials(true)
        .allowedMethods(
          setOf(
            HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH,
            HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.DELETE
          )
        ).allowedHeaders(
          mutableSetOf(
            HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
            HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
            HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
            HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
            HttpHeaders.X_REQUESTED_WITH,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.ACCEPT
          )
        )
    )
    val server = config.getJsonObject(SERVER)
    val eventBus = vertx.eventBus()
    name = config.getString("name")
    OauthHandler(
      OAuth2Auth.create(vertx, oauthConfig()),
      router, config.getJsonObject(OAUTH), eventBus
    )
    handlerRegister(router, eventBus)
    router.route().failureHandler(::exceptionHandler)
    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(server.getInteger("port", 8888)) {
        if (it.succeeded()) {
          log.info("Success start server on port {}......", server.getInteger("port", 8888))
        } else {
          log.error("Failed start server......", it.cause())
        }
      }
    consulClient = ConsulClient.create(vertx)
    val checkOptions = CheckOptions(
      jsonObjectOf(
        "http" to "http://127.0.0.1:8889/status",
        "interval" to "30s",
        "serviceId" to name,
        "name" to name
      )
    )
    val serviceOptions = ServiceOptions(
      jsonObjectOf(
        "name" to name,
        "id" to name,
        "checkOptions" to checkOptions
      )
    )
    consulClient.registerService(serviceOptions)
      .onSuccess {
        launch {
          consulClient.registerCheckAwait(checkOptions)
          log.info("Success service {} register to consul.", name)
        }
      }
      .onFailure { log.warn("Failed service {} register to consul: {}", name, it.cause?.message) }
    vertx.exceptionHandler {
      it.printStackTrace()
      throw it
    }
  }

  private fun handlerRegister(router: Router, eventBus: EventBus) {
    MeHandler(router, eventBus)
    EveryHandler(router, eventBus)
    RoleHandler(router, eventBus)
    ResHandler(router, eventBus)
    DataHandler(router, eventBus)
    SemesterHandler(router, eventBus)
    StudentHandler(router, eventBus)
    TeacherHandler(router, eventBus)
    UserHandler(router, eventBus)
    ClientHandler(router, eventBus)
  }

  /**
   * All handler before, it will get a [context] to config.
   * There are many handle by official.
   */
  private fun beforeHandler(context: RoutingContext) {
    context.response().putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
    context.next()
  }

  /**
   * All exception must be use
   */
  private fun exceptionHandler(failureRoutingContext: RoutingContext) {
    val exception = failureRoutingContext.failure()
    val response = failureRoutingContext.response()
    val message = jsonObjectOf("error" to exception.localizedMessage).toBuffer()
    when (exception) {
      is RuntimeException -> response.setStatusCode(500).end(message)
      is UnauthorizedException -> response.setStatusCode(401).end(message)
      is ForbiddenException -> response.setStatusCode(403).end(message)
      is BadRequestException -> response.setStatusCode(400).end(message)
      else -> response.setStatusCode(500).end(message)
    }
  }

  /**
   * Oauth config options.
   *
   * @return OAuth2ClientOptions
   */
  private fun oauthConfig(): OAuth2ClientOptions =
    OAuth2ClientOptions(
      jsonObjectOf(
        "clientID" to config.oauth(Oauth.CLIENT_ID),
        "clientSecret" to config.oauth(Oauth.CLIENT_SECRET),
        "site" to config.oauth(SERVER),
        "flow" to OAuth2FlowType.AUTH_CODE,
        "tokenPath" to config.oauth(Oauth.TOKEN),
        "authorizationPath" to config.oauth(Oauth.AUTHORIZATION),
        "introspectionPath" to config.oauth(Oauth.TOKEN_INFO),
        "scopeSeparator" to ","
      )
    )

  override suspend fun stop() {
    consulClient.deregisterServiceAwait(name)
    log.info("Success deregister service {}.", name)
    log.info("Success stop web verticle......")
  }
}
