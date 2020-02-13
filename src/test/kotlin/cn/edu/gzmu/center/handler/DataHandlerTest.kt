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
 * @date 2020/2/12 下午9:48
 */
class DataHandlerTest : OauthHelper() {
  @Test
  internal fun `Get college by type id when passed`(testContext: VertxTestContext) {
    client.get("/data/type/0")
      .send {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Get college by parent id when passed`(testContext: VertxTestContext) {
    client.get("/data/parent/1")
      .send {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Update college by id when passed`(testContext: VertxTestContext) {
    client.patch("/data")
      .sendJsonObject(jsonObjectOf(
        "id" to 10,
        "remark" to "test",
        "isEnable" to true
      )) {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Create college when passed`(testContext: VertxTestContext) {
    client.post("/data")
      .sendJsonObject(jsonObjectOf(
        "name" to "test",
        "parentId" to 0,
        "brief" to "test",
        "type" to 0,
        "sort" to 1
      )) {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(created, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Get page when passed`(testContext: VertxTestContext) {
    client.get("/data")
      .addQueryParam("sort", "id")
      .addQueryParam("page", "1")
      .addQueryParam("size", "5")
      .addQueryParam("name", "")
      .addQueryParam("type", "4")
      .send {
        pageCheck(testContext, it)
      }
  }

  @Test
  internal fun `Delete data when passed`(testContext: VertxTestContext) {
    client.delete("/data/10")
      .send {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }
}
