package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/18 下午8:31
 */
class UserHandlerTest : OauthHelper() {
  @Test
  internal fun `Get user one when passed`(testContext: VertxTestContext) {
    client.get("/user/1")
      .send {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertEquals(1, body.getInteger("id"))
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Reset user password when passed`(testContext: VertxTestContext) {
    client.patch("/user/password")
      .sendJsonObject(
        jsonObjectOf(
          "id" to 4,
          "userId" to 4
        )
      ) {
        noContentCheck(testContext, it)
      }
  }

  @Test
  internal fun `Update user when passed`(testContext: VertxTestContext) {
    client.patch("/user")
      .sendJsonObject(
        jsonObjectOf(
          "id" to 4,
          "name" to "studentTest",
          "email" to "li@163.com",
          "phone" to "13711111111"
        )
      ) {
        noContentCheck(testContext, it)
      }
  }
}
