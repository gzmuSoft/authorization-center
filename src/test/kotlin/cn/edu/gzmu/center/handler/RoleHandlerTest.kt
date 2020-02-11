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
 * @date 2020/2/11 下午1:03
 */
class RoleHandlerTest : OauthHelper() {

  @Test
  internal fun `Get role by parent id when passed`(testContext: VertxTestContext) {
    client.get("/role/parent/0")
      .send {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(ok, response.statusCode())
          val body = response.bodyAsJsonArray()
          assertEquals(5, body.size())
          testContext.completeNow()
        }
      }
  }

  @Test
  internal fun `Get res by role id when passed`(testContext: VertxTestContext) {
    client.get("/role/res/1")
      .send {
        resultCheck(testContext, it)
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
  internal fun `Update sys role when passed`(testContext: VertxTestContext) {
    client.patch("/role")
      .sendJsonObject(jsonObjectOf(
        "id" to 1,
        "remark" to "test"
      )) {
        resultCheck(testContext, it)
        val response = it.result()
        testContext.verify {
          assertEquals(noContent, response.statusCode())
          testContext.completeNow()
        }
      }
  }
}
