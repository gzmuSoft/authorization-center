package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.handler.EveryHandler
import cn.edu.gzmu.center.handler.MeHandler
import cn.edu.gzmu.center.handler.OauthHandler
import cn.edu.gzmu.center.handler.RoleHandler
import cn.edu.gzmu.center.model.BadRequestException
import cn.edu.gzmu.center.model.ForbiddenException
import cn.edu.gzmu.center.model.UnauthorizedException
import cn.edu.gzmu.center.model.extension.oauth
import cn.edu.gzmu.center.model.address.Oauth
import cn.edu.gzmu.center.model.address.Oauth.Companion.OAUTH
import cn.edu.gzmu.center.model.address.Oauth.Companion.SERVER
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.ext.auth.oauth2.OAuth2Auth
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
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

  /**
   * Will get config from application.conf and start Web server.
   */
  override suspend fun start() {
    val router = Router.router(vertx)
    router.route().handler(BodyHandler.create())
    router.route().handler(::beforeHandler)
    val server = config.getJsonObject(SERVER)
    val eventBus = vertx.eventBus()
    OauthHandler(
      OAuth2Auth.create(vertx, oauthConfig()),
      router, config.getJsonObject(OAUTH), eventBus
    )
    MeHandler(router, eventBus)
    EveryHandler(router, eventBus)
    RoleHandler(router, eventBus)
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
    vertx.exceptionHandler {
      log.error("Get a exception")
      throw it
    }
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
    log.info("Success stop web verticle......")
  }
}
