package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.core.Vertx
import io.vertx.junit5.VertxTestContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

  @Test
  internal fun `Get student info when passed`(testContext: VertxTestContext) {
    client.get("/me/info")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(200, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.containsKey("classesId"))
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Get teacher info when passed`(vertx: Vertx, testContext: VertxTestContext) {
    GlobalScope.launch {
      username = "teacher"
      oauthToken(vertx)
      client.get("/me/info")
        .send {
          if (it.failed()) testContext.failNow(it.cause())
          val response = it.result()
          testContext.verify {
            assertEquals(200, response.statusCode())
            val body = response.bodyAsJsonObject()
            assertTrue(body.containsKey("isAcademicLeader"))
            testContext.completeNow()
          }
        }
    }

  }

}
