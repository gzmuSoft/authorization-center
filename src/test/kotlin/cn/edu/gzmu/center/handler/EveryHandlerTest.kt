package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import cn.edu.gzmu.center.model.and
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/8 下午1:34
 */
internal class EveryHandlerTest : OauthHelper() {

  @Test
  internal fun `Get sys data by type when passed`(testContext: VertxTestContext) {
    client.get("/base/sysData/type/0")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonArray()
          assertTrue(body.size() > 0)
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Get sys data by types when passed`(testContext: VertxTestContext) {
    client.get("/base/sysData/types")
      .addQueryParam("types", "6" and "9")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.containsKey("6"))
          assertTrue(body.containsKey("9"))
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Get sys data name by id when passed`(testContext: VertxTestContext) {
    client.get("/base/sysData/info/4")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.containsKey("name"))
          assertTrue(body.containsKey("result"))
          assertTrue(body.getJsonArray("result").size() > 0)
          println(body.getString("name"))
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Get user exist with name when passed`(testContext: VertxTestContext) {
    client.get("/base/user/exist")
      .setQueryParam("name", "admin")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.getBoolean("exist"))
          testContext.completeNow()
        }
      }
  }
  @Test
  internal fun `Get user exist with email when passed`(testContext: VertxTestContext) {
    client.get("/base/user/exist")
      .setQueryParam("email", "lizhongyue246@163.com")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.getBoolean("exist"))
          testContext.completeNow()
        }
      }
  }
  @Test
  internal fun `Get user exist with phone when passed`(testContext: VertxTestContext) {
    client.get("/base/user/exist")
      .setQueryParam("phone", "13765308261")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.getBoolean("exist"))
          testContext.completeNow()
        }
      }
  }
  @Test
  internal fun `Get user does not exist when passed`(testContext: VertxTestContext) {
    client.get("/base/user/exist")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(400, response.statusCode())
          testContext.completeNow()
        }
      }
  }
}
