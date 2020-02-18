package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
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
}
