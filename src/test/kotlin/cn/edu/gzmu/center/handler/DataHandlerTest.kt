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
  internal fun `Test get college by type id`(testContext: VertxTestContext) {
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
  internal fun `Test get college by parent id`(testContext: VertxTestContext) {
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
  internal fun `Test update college`(testContext: VertxTestContext) {
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
  internal fun `Test create college`(testContext: VertxTestContext) {
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

}
