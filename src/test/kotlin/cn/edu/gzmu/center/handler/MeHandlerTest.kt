package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * User tests.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午10:07
 */
internal class MeHandlerTest : OauthHelper() {

  @Test
  internal fun `Get user routes when passed`(testContext: VertxTestContext) {
    client.get("/me/routes")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        else {
          val response = it.result()
          testContext.verify {
            assertEquals(200, response.statusCode())
            val body = response.bodyAsJsonObject()
            assertTrue(body.getBoolean("index"))
            testContext.completeNow()
          }
        }
      }
  }

  @Test
  internal fun `Get user menu when passed`(testContext: VertxTestContext) {
    client.get("/me/menu")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(200, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.containsKey("menus"))
          testContext.completeNow()
        }
      }
  }

}
