package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 下午1:27
 */
class ResHandlerTest : OauthHelper() {

  @Test
  internal fun `Get res page`(testContext: VertxTestContext) {
    client.get("/res")
      .addQueryParam("sort", "id")
      .addQueryParam("page", "1")
      .addQueryParam("size", "5")
      .addQueryParam("describe", "")
      .addQueryParam("type", "0")
      .send {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonObject()
          assertTrue(body.containsKey("content"))
          assertTrue(body.containsKey("itemsLength"))
          assertEquals(5, body.getJsonArray("content").size())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Update res when passed`(testContext: VertxTestContext) {
    client.patch("/res")
      .sendJsonObject(
        jsonObjectOf(
          "id" to 1,
          "spell" to "test"
        )
      ) {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Delete res when passed`(testContext: VertxTestContext) {
    client.delete("/res/1000")
      .send {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Insert into res when passed`(testContext: VertxTestContext) {
    client.post("/res")
      .sendJsonObject(jsonObjectOf(
        "name" to "test"
      )) {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(created, response.statusCode())
          testContext.completeNow()
        }
      }
  }
}
