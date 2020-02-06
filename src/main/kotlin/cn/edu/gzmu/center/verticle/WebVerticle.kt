package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.config.ApplicationConfig
import cn.edu.gzmu.center.me.MeHandler
import cn.edu.gzmu.center.model.ForbiddenException
import cn.edu.gzmu.center.model.UnauthorizedException
import cn.edu.gzmu.center.oauth.Oauth.Companion.OAUTH
import cn.edu.gzmu.center.oauth.Oauth.Companion.SERVER
import cn.edu.gzmu.center.oauth.OauthHandler
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.ext.auth.oauth2.OAuth2Auth
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
   * Will get config from [ApplicationConfig] and start Web server.
   */
  override suspend fun start() {
    val router = Router.router(vertx)
    val applicationConfig = ApplicationConfig(vertx)
    router.route().handler(BodyHandler.create())
    router.route().handler(::beforeHandler)
    val server = applicationConfig.config().getJsonObject(SERVER)
    val eventBus = vertx.eventBus()
    OauthHandler(
      OAuth2Auth.create(vertx, applicationConfig.oauthConfig()),
      router, applicationConfig.config().getJsonObject(OAUTH), eventBus
    )
    MeHandler(router, eventBus)
    router.route().last().failureHandler(::exceptionHandler)
    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(server.getInteger("port", 8888)) {
        if (it.succeeded()) {
          log.info("Server start on port {}......", server.getInteger("port", 8888))
        } else {
          log.error("Server failed......", it.cause())
        }
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
    }
  }
}
