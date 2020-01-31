package cn.edu.gzmu.center

import cn.edu.gzmu.center.config.ApplicationConfig
import cn.edu.gzmu.center.model.ForbiddenException
import cn.edu.gzmu.center.model.UnauthorizedException
import cn.edu.gzmu.center.oauth.OAUTH
import cn.edu.gzmu.center.oauth.OauthHandler
import cn.edu.gzmu.center.oauth.SERVER
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.impl.logging.Logger
import io.vertx.core.impl.logging.LoggerFactory
import io.vertx.ext.auth.oauth2.OAuth2Auth
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class MainVerticle : CoroutineVerticle() {

  private val log: Logger = LoggerFactory.getLogger(MainVerticle::class.java.name)

  override fun start(startFuture: Promise<Void>?) {
    val router = Router.router(vertx)
    val applicationConfig = ApplicationConfig(vertx)
    router.route().handler(BodyHandler.create())
    router.route().handler(::beforeHandler)
    launch {
      val server = applicationConfig.config().getJsonObject(SERVER)
      OauthHandler(
        OAuth2Auth.create(
          vertx, applicationConfig.oauthConfig()
        ), router, applicationConfig.config().getJsonObject(OAUTH)
      ).router()
      router.route().failureHandler(::exceptionHandler)
      vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(server.getInteger("port", 8888)) {
          if (it.succeeded()) {
            log.info("Server start......")
            startFuture?.complete()
          } else {
            log.error("Server failed......${it.cause().printStackTrace()}")
            startFuture?.fail(it.cause())
          }
        }
    }
  }

  private fun beforeHandler(context: RoutingContext) {
    context.response().putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
    context.next()
  }

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


fun main() {
  Vertx.vertx().deployVerticle(MainVerticle::class.java.name)
}
