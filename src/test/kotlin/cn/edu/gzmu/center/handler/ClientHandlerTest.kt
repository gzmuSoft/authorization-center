package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/23 下午8:21
 */
class ClientHandlerTest : OauthHelper() {

  @Test
  internal fun `Get clients when passed`(testContext: VertxTestContext) {
    client.get("/client")
      .send {
        okCheck(testContext, it)
      }
  }

  @Test
  internal fun `Add client when passed`(testContext: VertxTestContext) {
    client.post("/client")
      .sendJsonObject(
        jsonObjectOf(
          "id" to null,
          "name" to "test${(100..999).random()}",
          "spell" to null,
          "clientId" to "test${(100..999).random()}",
          "resourceIds" to "test${(100..999).random()}",
          "clientSecret" to "${(100..999).random()}",
          "scope" to "READ",
          "grantTypes" to "password,refresh_token,sms,email,authorization_code",
          "redirectUrl" to "httpto//127.0.0.1to8081/#/login",
          "accessTokenValidity" to 600000,
          "refreshTokenValidity" to 600000,
          "sort" to 1,
          "remark" to "test${(100..999).random()}",
          "isEnable" to true
        )
      ) {
        createCheck(testContext, it)
      }

  }
}
