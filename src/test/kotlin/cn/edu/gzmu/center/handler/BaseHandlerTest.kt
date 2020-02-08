package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
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
internal class BaseHandlerTest : OauthHelper() {

  @Test
  internal fun `Get sys data by type when passed`(testContext: VertxTestContext) {
    client.get("/base/sysData/type/0")
      .send {
        if (it.failed()) testContext.failNow(it.cause())
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonArray()
          assertEquals(2, body.size())
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
          assertEquals(4, body.getJsonArray("result").size())
          println(body.getString("name"))
          testContext.completeNow()
        }
      }
  }
}
