package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.model.DatabaseException
import cn.edu.gzmu.center.model.ForbiddenException
import cn.edu.gzmu.center.model.UnauthorizedException
import cn.edu.gzmu.center.model.address.Address.Companion.RESULT
import cn.edu.gzmu.center.model.address.Oauth.Companion.ADDRESS_ME
import cn.edu.gzmu.center.model.address.Oauth.Companion.ADDRESS_ROLE_RESOURCE
import cn.edu.gzmu.center.model.address.Oauth.Companion.CLIENT_ID
import cn.edu.gzmu.center.model.address.Oauth.Companion.CODE
import cn.edu.gzmu.center.model.address.Oauth.Companion.LOGOUT_REDIRECT_URL
import cn.edu.gzmu.center.model.address.Oauth.Companion.LOGOUT_URI
import cn.edu.gzmu.center.model.address.Oauth.Companion.REDIRECT_URI
import cn.edu.gzmu.center.model.address.Oauth.Companion.SCOPE
import cn.edu.gzmu.center.model.address.Oauth.Companion.SECURITY
import cn.edu.gzmu.center.model.address.Oauth.Companion.SERVER
import cn.edu.gzmu.center.util.AntPathMatcher
import io.netty.handler.codec.http.HttpHeaderNames
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
 * @apiDefine Bearer
 * @apiHeader {String} Authorization Bearer token.
 *
 * When login success, this route context will save these data:
 * username - Login user's name
 * resource - User can access resources
 * id - User id from sys_user
 * email - User email
 * phone - User phone
 * is_student - if true, the user's role has student
 * is_teacher - if true, the user's role has teacher
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
    router.post("/oauth/check_token").handler(::checkToken)
    router.post("/oauth/refresh_token").handler(::refreshToken)
    // Add RBAC
    router.route().handler(::authentication)
    router.route().handler(::userInfo)
    router.get("/oauth/me").handler(::me)
  }

  /**
   * Setting user info.
   */
  private fun authenticate(context: RoutingContext) {
    val authorization = context.request().headers()[HttpHeaderNames.AUTHORIZATION] ?: ""
    if (!authorization.startsWith("Bearer ")) context.fail(UnauthorizedException())
    context.put("bearer", authorization)
    oAuth2Auth.authenticate(
      jsonObjectOf(
        "token_type" to "Bearer",
        "access_token" to authorization.substring(7)
      )
    ) {
      if (it.failed()) {
        context.fail(UnauthorizedException(it.cause().localizedMessage))
      } else {
        log.debug("Login user is: {}", it.result().principal().getString("sub"))
        context.setUser(it.result())
        context.put("username", it.result().principal().getString("sub"))
        context.next()
      }
    }
  }

  /**
   * @api {GET} /oauth/server oauth server login
   * @apiVersion 1.0.0
   * @apiName OauthLogin
   * @apiDescription Get remote authorization server logout url.
   * @apiGroup Oauth
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/oauth/server'
   * @apiSuccess {String}   server    authorization server login url.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *      {
   *        "server": "http://......"
   *      }
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
   * @api {POST} /oauth/token oauth code token
   * @apiVersion 1.0.0
   * @apiName OauthTokenCode
   * @apiDescription Get token info by authorization code.
   * @apiParam {String} code authorization code.
   * @apiGroup Oauth
   * @apiSuccess {String}   access_token    token.
   * @apiSuccess {String}   token_type      always bearer.
   * @apiSuccess {String}   refresh_token   refresh token.
   * @apiSuccess {Long}     expires_in      expires times.
   * @apiSuccess {String}   sub             user name.
   * @apiSuccess {Boolean}  is_teacher      if have ROLE_TEACHER.
   * @apiSuccess {Boolean}  is_student      if have ROLE_STUDENT.
   * @apiSuccess {String}   user_name       user name.
   * @apiSuccess {Long}     nbf             not before.
   * @apiSuccess {Long}     iat             issued at.
   * @apiSuccess {String[]} authorities     user authorities.
   * @apiSuccess {String}   jti             JWT ID.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *      {
   *        "access_token": "......",
   *        "token_type": "bearer",
   *        "refresh_token": "......",
   *        "expires_in": 586103,
   *        "scope": "...",
   *        "sub": "admin",
   *        "nbf": 1581047798,
   *        "is_teacher": false,
   *        "user_name": "admin",
   *        "iat": 1581047798,
   *        "is_student": true,
   *        "authorities": [
   *          "ROLE_ADMIN",
   *          "ROLE_STUDENT"
   *        ],
   *        "jti": "..."
   *      }
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
      else context.response().end(JsonObject.mapFrom(it.result().principal()).toBuffer())
    }
  }

  /**
   * @api {GET} /oauth/logout oauth server logout
   * @apiVersion 1.0.0
   * @apiName OauthLogout
   * @apiDescription Get remote authorization server logout url.
   *                  The client logout authorization server url.
   * @apiGroup Oauth
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/oauth/logout'
   * @apiSuccess {String}   server    authorization server logout url.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *      {
   *        "server": "http://......"
   *      }
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
   * @api {POST} /oauth/check_token oauth server check token
   * @apiVersion 1.0.0
   * @apiName OauthCheckToken
   * @apiDescription Get token info.
   * @apiGroup Oauth
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/oauth/check_token' \
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {String}    sub            user name.
   * @apiSuccess {String}    user_name      user name.
   * @apiSuccess {Boolean}   active         token active.
   * @apiSuccess {Boolean}   is_student     if has ROLE_STUDENT.
   * @apiSuccess {Boolean}   is_teacher     if has ROLE_TEACHER.
   * @apiSuccess {String[]}  authorities    user roles.
   * @apiSuccess {String}    client_id      client id.
   * @apiSuccess {String[]}  aud            client id.
   * @apiSuccess {Long}      nbf            not before.
   * @apiSuccess {String[]}  scope          client scopes.
   * @apiSuccess {Long}      exp            expires times.
   * @apiSuccess {Long}      iat            issue at times.
   * @apiSuccess {String}    jti            jwt id.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *       {
   *         "sub": "admin",
   *         "user_name": "admin",
   *         "active": true,
   *         "is_student": true,
   *         "authorities": [
   *           "ROLE_ADMIN",
   *           "ROLE_STUDENT"
   *         ],
   *         "client_id": "gzmu-auth",
   *         "aud": [
   *           "gzmu-auth"
   *         ],
   *         "nbf": 1581047798,
   *         "is_teacher": false,
   *         "scope": [
   *           "READ"
   *         ],
   *         "exp": 1581647798,
   *         "iat": 1581047798,
   *         "jti": "aafe976b-e360-4148-8d45-4553b33703bc"
   *       }
   */
  private fun checkToken(context: RoutingContext) {
    context.response().end(context.user().principal().toString())
  }

  /**
   * @api {POST} /oauth/refresh_token  oauth server refresh token
   * @apiVersion 1.0.0
   * @apiName OauthRefreshToken
   * @apiDescription Get new token info by refresh token.
   * @apiGroup Oauth
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/oauth/refresh_token' \
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {String}   access_token    token.
   * @apiSuccess {String}   token_type      always bearer.
   * @apiSuccess {String}   refresh_token   refresh token.
   * @apiSuccess {Long}     expires_in      expires times.
   * @apiSuccess {String}   sub             user name.
   * @apiSuccess {Boolean}  is_teacher      if have ROLE_TEACHER.
   * @apiSuccess {Boolean}  is_student      if have ROLE_STUDENT.
   * @apiSuccess {String}   user_name       user name.
   * @apiSuccess {Long}     nbf             not before.
   * @apiSuccess {Long}     iat             issued at.
   * @apiSuccess {String[]} authorities     user authorities.
   * @apiSuccess {String}   jti             JWT ID.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *      {
   *        "access_token": "......",
   *        "token_type": "bearer",
   *        "refresh_token": "......",
   *        "expires_in": 586103,
   *        "scope": "...",
   *        "sub": "admin",
   *        "nbf": 1581047798,
   *        "is_teacher": false,
   *        "user_name": "admin",
   *        "iat": 1581047798,
   *        "is_student": true,
   *        "authorities": [
   *          "ROLE_ADMIN",
   *          "ROLE_STUDENT"
   *        ],
   *        "jti": "..."
   *      }
   */
  private fun refreshToken(context: RoutingContext) {
    // In default implementation, it will get refresh token from context user, but
    // in spring security oauth, it doesn't have this field when check token.
    // We have to set it up manually.
    context.user().principal().put("refresh_token", context.bodyAsJson.getString("refresh_token"))
    oAuth2Auth.refresh(context.user()) {
      if (it.failed()) context.fail(UnauthorizedException(it.cause().localizedMessage))
      else context.response().end(JsonObject.mapFrom(it.result().principal()).toBuffer())
    }
  }

  /**
   * @api {GET} /oauth/me oauth me
   * @apiVersion 1.0.0
   * @apiName OauthMe
   * @apiDescription Current user info.
   * @apiGroup Oauth
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/oauth/me'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {String}   name      user name.
   * @apiSuccess {String}   email     user email.
   * @apiSuccess {String}   avatar    user avatar.
   * @apiSuccess {String}   image     user image.
   * @apiSuccess {String}   phone     user phone.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *      {
   *        "name": "admin",
   *        "email": "lizhongyue248@163.com",
   *        "avatar": "http://image.japoul.cn/me.jpg",
   *        "image": "http://image.japoul.cn/me.jpg",
   *        "phone": "13765308262"
   *      }
   */
  private fun me(context: RoutingContext) {
    context.response().end(
      jsonObjectOf(
        "name" to context.get<String>("name"),
        "email" to context.get<String>("email"),
        "avatar" to context.get<String>("avatar"),
        "image" to context.get<String>("image"),
        "phone" to context.get<String>("phone")
      ).toString()
    )
  }

  /**
   * Verify that user have permission to access the resource.
   */
  private fun authentication(context: RoutingContext) {
    val roles = context.user().principal().getJsonArray("authorities")
    eventBus.request<JsonObject>(ADDRESS_ROLE_RESOURCE, roles) {
      if (it.failed()) {
        context.fail(DatabaseException(it.cause().localizedMessage))
        return@request
      }
      val resources = it.result().body()
      val uri = context.request().path()
      val method = context.request().method()
      val match = resources.getJsonArray(RESULT).map { res -> res as JsonObject }
        .find { res ->
          // url match.
          matcher.match(res.getString("url") ?: "", uri)
            && method.name == res.getString("method") // uri and method must match.
            && (res.getString("role") == "ROLE_PUBLIC" // If this resource is public, everyone can access.
            || roles.contains(res.getString("role"))) // If this resource need authentication, the role must match.
        }
      if (Objects.isNull(match)) {
        context.fail(ForbiddenException())
        log.debug("Forbidden!")
      } else {
        log.debug("Current user can access resource.")
        // Save this user can access resources.
        context.put("resource", resources.getJsonArray(RESULT))
        context.next()
      }
    }
  }

  /**
   * Add user info.
   */
  private fun userInfo(context: RoutingContext) {
    val username = context.get<String>("username")
    eventBus.request<JsonObject>(ADDRESS_ME, username) {
      if (it.failed()) {
        context.fail(DatabaseException(it.cause().localizedMessage))
      } else {
        it.result().body().map.forEach { (key, value) -> context.put(key, value) }
        context.next()
      }
    }
  }

}
