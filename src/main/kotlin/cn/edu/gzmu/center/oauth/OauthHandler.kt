package cn.edu.gzmu.center.oauth

import cn.edu.gzmu.center.model.UnauthorizedException
import io.netty.handler.codec.http.HttpHeaderNames
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.oauth2.OAuth2Auth
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Oauth handler.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/23 上午11:44
 */
class OauthHandler(private val oAuth2Auth: OAuth2Auth,
                   private val router: Router, private val config: JsonObject) {
  private val log: Logger = LoggerFactory.getLogger(OauthHandler::class.java.name)

  fun router() {
    router.get("/oauth/server").handler(this::server)
    router.post("/oauth/token").handler(this::token)
    router.get("/oauth/logout").handler(this::logoutUrl)
    // The following routes must be authorized.
    router.route().handler(this::authenticate)
    router.post("/oauth/refresh_token").handler(this::refreshToken)
  }

  /**
   * Setting user info.
   *
   * @param context RoutingContext
   */
  private fun authenticate(context: RoutingContext) {
    val authorization = context.request().headers()[HttpHeaderNames.AUTHORIZATION] ?: ""
    if (!authorization.startsWith("Bearer ")) throw UnauthorizedException()
    oAuth2Auth.authenticate(
      jsonObjectOf(
        "token_type" to "Bearer",
        "access_token" to authorization.substring(7)
      )
    ) {
      if (it.failed()) context.fail(UnauthorizedException(it.cause().localizedMessage))
      log.debug("Login user is: {}", it.result().principal().getString("sub"))
      context.setUser(it.result())
      context.next()
    }
  }

  /**
   * Get remote authorization server url.
   *
   * @param context RoutingContext
   */
  private fun server(context: RoutingContext) {
    val authorizeURL = oAuth2Auth.authorizeURL(
      jsonObjectOf(
        "redirect_uri" to config.getString(REDIRECT_URI),
        "scope" to config.getString(SCOPE)
      )
    )
    context.response().end(jsonObjectOf(SERVER to authorizeURL).toBuffer())
  }

  /**
   * Get token info by authorization code.
   *
   * @param context RoutingContext
   */
  private fun token(context: RoutingContext) {
    val code = context.bodyAsJson.getString(CODE)
    oAuth2Auth.authenticate(
      jsonObjectOf(
        "code" to code,
        "redirect_uri" to config.getString(REDIRECT_URI)
      )
    ) {
      if (it.failed()) context.fail(UnauthorizedException(it.cause().localizedMessage))
      context.response().end(JsonObject.mapFrom(it.result().principal()).toBuffer())
    }
  }

  /**
   * The client logout authorization server url.
   *
   * @param context RoutingContext
   */
  private fun logoutUrl(context: RoutingContext) {
    // Logging out is not a Oauth2 feature but it is present on OpenID Connect.
    // In spring boot security, I have our logout out url, We must let user redirect this url.
    context.response().end(jsonObjectOf(SERVER to logoutUrl(config)).toBuffer())
  }

  /**
   * Get new token info by refresh token.
   *
   * @param context RoutingContext
   */
  private fun refreshToken(context: RoutingContext) {
    // In default implementation, it will get refresh token from context user, but
    // in spring security oauth, it doesn't have this field when check token.
    // We have to set it up manually.
    context.user().principal().put("refresh_token", context.bodyAsJson.getString("refresh_token"))
    oAuth2Auth.refresh(context.user()) {
      if (it.failed()) context.fail(UnauthorizedException(it.cause().localizedMessage))
      context.response().end(JsonObject.mapFrom(it.result().principal()).toBuffer())
    }
  }

}
