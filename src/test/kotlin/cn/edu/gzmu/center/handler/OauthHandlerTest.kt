package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.config.ApplicationConfig
import cn.edu.gzmu.center.model.extension.oauth
import cn.edu.gzmu.center.oauth.CODE
import cn.edu.gzmu.center.oauth.SERVER
import cn.edu.gzmu.center.verticle.WebVerticle
import io.netty.handler.codec.http.HttpHeaderNames
import io.vertx.core.MultiMap
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.client.WebClientSession
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.kotlin.ext.web.client.sendAwait
import io.vertx.kotlin.ext.web.client.sendFormAwait
import io.vertx.kotlin.ext.web.client.sendJsonObjectAwait
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URI
import java.util.*
import java.util.regex.Pattern

/**
 * Oauth handler Test.
 *
 * This test will test all routes of oauth.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/30 上午11:46
 */
@ExtendWith(VertxExtension::class)
class OauthHandlerTest {

  private lateinit var client: WebClient
  private lateinit var application: ApplicationConfig
  private var username: String = "admin"
  private var password: String = "1997"

  @BeforeEach
  fun deployVerticle(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(WebVerticle(), testContext.succeeding<String> {
      // Setting default host and default port.
      // These config come from application.yml,
      // maybe change to git config.
      val options = WebClientOptions()
      options.defaultHost = "localhost"
      options.defaultPort = 9000
      client = WebClient.create(vertx, options)
      application = ApplicationConfig(vertx)
      testContext.completeNow()
    })
  }

  @Test
  fun `Get oauth server url when passed`(testContext: VertxTestContext) {
    client.get("/oauth/server")
      .`as`(BodyCodec.jsonObject())
      .send {
        if (it.succeeded()) {
          assertTrue(it.result().body().containsKey(SERVER))
          testContext.completeNow()
        } else testContext.failNow(it.cause())
      }
  }

  @Test
  fun `Get token info by authorization code Test`(vertx: Vertx, testContext: VertxTestContext) {
    // launch coroutine.
    GlobalScope.launch(vertx.dispatcher()) {
      val accessToken = oauthToken(vertx)
      assertNotNull(accessToken)
      testContext.completeNow()
    }
  }

  @Test
  fun `The client logout authorization server url`(testContext: VertxTestContext) {
    client.get("/oauth/logout").send {
      if (it.succeeded()) {
        val result = it.result().bodyAsJsonObject()
        assertTrue(result.containsKey(SERVER))
        testContext.completeNow()
      } else {
        testContext.failNow(it.cause())
      }
    }
  }

  @Test
  fun `Get new token info by refresh token`(vertx: Vertx, testContext: VertxTestContext) {
    GlobalScope.launch(vertx.dispatcher()) {
      val accessToken = oauthToken(vertx, "access_token")
      val refreshToken = oauthToken(vertx, "refresh_token")
      val tokenResponse = client.post("/oauth/refresh_token")
        .bearerTokenAuthentication(accessToken)
        .sendJsonObjectAwait(jsonObjectOf("refresh_token" to refreshToken))
      val token = tokenResponse.bodyAsJsonObject()

      // Get new access token and new refresh token.
      assertTrue(token.containsKey("access_token"))
      assertTrue(token.containsKey("refresh_token"))
      testContext.completeNow()
    }
  }

  /**
   * Get oauth token.
   *
   * @param vertx need vertx to create webclient
   * @param key result key
   * @return response
   */
  private suspend fun oauthToken(vertx: Vertx, key: String = "access_token"): String {
    val code = oauthCode(vertx)
    val tokenResponse =
      client.post("/oauth/token").sendJsonObjectAwait(jsonObjectOf(CODE to code))
    val token = tokenResponse.bodyAsJsonObject()
    assertTrue(token.containsKey(key))
    return token.getString(key)
  }

  /**
   * Get authorization code.
   *
   * @param vertx need vertx to create webclient
   */
  private suspend fun oauthCode(vertx: Vertx): String {
    val clientId = application.config().oauth("client-id")
    val clientSecret = application.config().oauth("client-secret")
    val secret = "Basic " + Base64.getEncoder().encode("$clientId:$clientSecret".toByteArray())
    val config = application.config()

    // 1. Get login page, save _csrf value.
    val serverResponse = client.get(9000, "localhost", "/oauth/server").sendAwait()
    val oauthServer = WebClientSession.create(WebClient.create(vertx))
    oauthServer.addHeader(HttpHeaderNames.AUTHORIZATION.toString(), secret)
    val loginResponse = oauthServer.getAbs(serverResponse.bodyAsJsonObject().getString(SERVER)).sendAwait()
    val matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*")
      .matcher(loginResponse.bodyAsString())
    assertTrue(matcher.find(1))

    // 2. Add login form.
    val form: MultiMap = MultiMap.caseInsensitiveMultiMap()
    // You can change username and password.
    form["username"] = username
    form["password"] = password
    form["_csrf"] = matcher.group(1)

    // 3. Login and save success cookies
    val formResponse =
      oauthServer.postAbs(config.oauth("server") + config.oauth("authorization-form"))
        .sendFormAwait(form)
    val headers = formResponse.headers()
    val location = headers[HttpHeaderNames.LOCATION]
    assertNotNull(location)

    // 4. Confirm authorize
    var response = oauthServer.getAbs(location)
      .followRedirects(false).sendAwait()

    // If the user already confirm, the response will is null,
    // It will redirect to our url. So we do not do these
    if (Objects.nonNull(response.body())) {
      val confirmMatcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*")
        .matcher(response.bodyAsString())
      assertTrue(confirmMatcher.find(1))
      val confirm: MultiMap = MultiMap.caseInsensitiveMultiMap()
      confirm["user_oauth_approval"] = "true"
      confirm["scope.${config.oauth("scope")}"] = "true"
      confirm["_csrf"] = confirmMatcher.group(1)

      // Get authorization code
      response = oauthServer
        .postAbs(config.oauth("server") + config.oauth("authorization"))
        .followRedirects(false)
        .sendFormAwait(confirm)
    }

    // 5. Get code from location.
    val codeLocation = URI(response.headers()[HttpHeaderNames.LOCATION])
    val code = codeLocation.query.substringAfter("=")
    assertNotNull(code)

    return code
  }

}
