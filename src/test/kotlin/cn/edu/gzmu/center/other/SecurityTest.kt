package cn.edu.gzmu.center.other

import cn.edu.gzmu.center.OauthHelper
import cn.edu.gzmu.center.model.extension.Address.Companion.LOG_ROUNDS
import cn.edu.gzmu.center.model.extension.oauth
import cn.edu.gzmu.center.oauth.Oauth.Companion.SERVER
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mindrot.jbcrypt.BCrypt

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/9 上午12:29
 */
internal class SecurityTest : OauthHelper() {

  @Test
  internal fun `The same encode method when passed`(vertx: Vertx, testContext: VertxTestContext) {
    val encode = BCrypt.hashpw("123456", BCrypt.gensalt(LOG_ROUNDS))
    val oauthServer = WebClient.create(vertx)
    oauthServer.getAbs(webConfig.oauth(SERVER) + "/auth/match")
      .bearerTokenAuthentication(token)
      .addQueryParam("password", "123456")
      .addQueryParam("encode", encode)
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        testContext.verify {
          assertEquals(ok, it.result().statusCode())
          assertEquals("true", it.result().body().toString())
          testContext.completeNow()
        }
      }
  }
}

internal class PasswordTest {
  @Test
  internal fun `Different result for same password when passed`() {
    val password1 = BCrypt.hashpw("123456", BCrypt.gensalt(12))
    val password2 = BCrypt.hashpw("123456", BCrypt.gensalt(12))
    val password3 = BCrypt.hashpw("123456", BCrypt.gensalt(12))
    val password4 = BCrypt.hashpw("123456", BCrypt.gensalt(12))
    assertAll({
      assertNotEquals(password1, password2)
      assertNotEquals(password1, password3)
      assertNotEquals(password1, password4)
    }, {
      assertNotEquals(password2, password3)
      assertNotEquals(password2, password4)
    }, {
      assertNotEquals(password3, password4)
    })
  }
}
