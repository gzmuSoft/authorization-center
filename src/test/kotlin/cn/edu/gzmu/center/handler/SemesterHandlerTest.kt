package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/14 下午12:55
 */
class SemesterHandlerTest : OauthHelper() {
  @Test
  internal fun `Get page when passed`(testContext: VertxTestContext) {
    client.get("/semester")
      .setQueryParam("schoolId", "1")
      .send {
        pageCheck(testContext, it)
      }
  }

  @Test
  internal fun `Update semester when passed`(testContext: VertxTestContext) {
    client.patch("/semester")
      .sendJsonObject(jsonObjectOf(
        "id" to 1,
        "name" to "test",
        "startDate" to "2018-12-15",
        "endDate" to "2018-12-15",
        "isEnable" to true
      )) {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          Assertions.assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Create semester when passed`(testContext: VertxTestContext) {
    client.post("/semester")
      .sendJsonObject(jsonObjectOf(
        "name" to "test",
        "startDate" to "2018-12-15",
        "endDate" to "2018-12-15",
        "schoolId" to 1,
        "isEnable" to true
      )) {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          Assertions.assertEquals(created, response.statusCode())
          testContext.completeNow()
        }
      }
  }
}
