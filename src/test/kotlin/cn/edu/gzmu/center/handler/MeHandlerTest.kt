package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.core.Vertx
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
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
            assertEquals(ok, response.statusCode())
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
          assertEquals(ok, response.statusCode())
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
          assertEquals(ok, response.statusCode())
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
            assertEquals(ok, response.statusCode())
            val body = response.bodyAsJsonObject()
            assertTrue(body.containsKey("isAcademicLeader"))
            testContext.completeNow()
          }
        }
    }
  }

  @Test
  internal fun `Update admin user when passed`(testContext: VertxTestContext) {
    client.patch("/me/user")
      .sendJsonObject(
        jsonObjectOf(
          "name" to "admin",
          "email" to "echo248@163.com",
          "phone" to "13765308262"
        )
      ) {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Update admin entity when passed`(testContext: VertxTestContext) {
    client.patch("/me/info")
      .sendJsonObject(
        jsonObjectOf(
          "name" to "admin",
          "remark" to "test update ${(0..10).random()})"
        )
      ) {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Update teacher entity when passed`(vertx: Vertx, testContext: VertxTestContext) {
    GlobalScope.launch {
      username = "teacher"
      oauthToken(vertx)
      client.patch("/me/info")
        .sendJsonObject(
          jsonObjectOf(
            "name" to "teacher",
            "remark" to "teacher update ${(0..10).random()})"
          )
        ) {
          if (it.failed()) testContext.failNow(it.cause())
          val response = it.result()
          testContext.verify {
            assertEquals(noContent, response.statusCode())
            testContext.completeNow()
          }
        }
    }
  }
}
