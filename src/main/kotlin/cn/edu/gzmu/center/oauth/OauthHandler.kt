package cn.edu.gzmu.center.oauth

import cn.edu.gzmu.center.model.DatabaseException
import cn.edu.gzmu.center.model.ForbiddenException
import cn.edu.gzmu.center.model.UnauthorizedException
import cn.edu.gzmu.center.model.extension.Address.Companion.RESULT
import cn.edu.gzmu.center.oauth.Oauth.Companion.ADDRESS_ME
import cn.edu.gzmu.center.oauth.Oauth.Companion.ADDRESS_ROLE_RESOURCE
import cn.edu.gzmu.center.oauth.Oauth.Companion.CLIENT_ID
import cn.edu.gzmu.center.oauth.Oauth.Companion.CODE
import cn.edu.gzmu.center.oauth.Oauth.Companion.LOGOUT_REDIRECT_URL
import cn.edu.gzmu.center.oauth.Oauth.Companion.LOGOUT_URI
import cn.edu.gzmu.center.oauth.Oauth.Companion.REDIRECT_URI
import cn.edu.gzmu.center.oauth.Oauth.Companion.SCOPE
import cn.edu.gzmu.center.oauth.Oauth.Companion.SECURITY
import cn.edu.gzmu.center.oauth.Oauth.Companion.SERVER
import cn.edu.gzmu.center.util.AntPathMatcher
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.oauth2.OAuth2Auth
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*

/**
 * Oauth handler.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/23 上午11:44
 */
class OauthHandler(
  private val oAuth2Auth: OAuth2Auth, router: Router,
  private val config: JsonObject, private val eventBus: EventBus
) {
  private val log: Logger = LoggerFactory.getLogger(OauthHandler::class.java.name)
  private val matcher: AntPathMatcher = AntPathMatcher()

  init {
    router.get("/oauth/server").handler(::server)
    router.post("/oauth/token").handler(::token)
    router.get("/oauth/logout").handler(::logoutUrl)
    // The following routes must be authorized.
    router.route().handler(::authenticate)
    // These api can access for all users.
    router.get("/oauth/me").handler(::me)
    router.post("/oauth/check_token").handler(::checkToken)
    router.post("/oauth/refresh_token").handler(::refreshToken)
    // Add RBAC
    router.route().handler(::authentication)
  }

  /**
   * Setting user info.
   */
  private fun authenticate(context: RoutingContext) {
    val authorization = context.request().headers()[HttpHeaderNames.AUTHORIZATION] ?: ""
    if (!authorization.startsWith("Bearer ")) context.fail(UnauthorizedException())
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
   */
  private fun logoutUrl(context: RoutingContext) {
    val url = config.getString(SERVER) + config.getString(LOGOUT_URI)
    val params = "?redirect_url=${URLEncoder.encode(config.getString(LOGOUT_REDIRECT_URL), UTF_8)}" +
      if (config.getBoolean(SECURITY)) "&client_id=${config.getString(CLIENT_ID)}"
      else ""
    // Logging out is not a Oauth2 feature but it is present on OpenID Connect.
    // In spring boot security, I have our logout out url, We must let user redirect this url.
    context.response().end(jsonObjectOf(SERVER to "${url}${params}").toBuffer())
  }

  /**
   * Get token info.
   */
  private fun checkToken(context: RoutingContext) {
    context.response().end(context.user().principal().toString())
  }

  /**
   * Get new token info by refresh token.
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

  /**
   * Verify that user have permission to access the resource.
   */
  private fun authentication(context: RoutingContext) {
    val roles = context.user().principal().getJsonArray("authorities")
    eventBus.request<JsonObject>(ADDRESS_ROLE_RESOURCE, roles) {
      if (it.failed()) context.fail(DatabaseException(it.cause().localizedMessage))
      val resources = it.result().body()
      val uri = context.request().uri()
      val method = context.request().method()
      val match = resources.getJsonArray(RESULT).map { res -> res as JsonObject }
        .find { res ->
          // url match.
          matcher.match(res.getString("url") ?: "", uri)
            && (res.getString("role") == "ROLE_PUBLIC" // If this resource is public, everyone can access.
            || method.name() == res.getString("method")) // If this resource need authentication, the method must match.
        }
      if (Objects.isNull(match)) {
        context.fail(ForbiddenException())
        log.debug("Forbidden!")
      }
      log.debug("Current user can access resource.")
      context.next()
    }
  }

  /**
   * Current user info.
   */
  private fun me(context: RoutingContext) {
    val username = context.user().principal().getString("sub")
    eventBus.request<JsonObject>(ADDRESS_ME, username) {
      if (it.failed()) context.fail(DatabaseException(it.cause().localizedMessage))
      context.response()
        .setStatusCode(HttpResponseStatus.OK.code())
        .end(it.result().body().toString())
    }
  }

}
