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
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/14 下午9:34
 */
class StudentHandlerTest : OauthHelper() {
  @Test
  internal fun `Student classmate test`(vertx: Vertx, testContext: VertxTestContext) {
    GlobalScope.launch {
      username = "student"
      oauthToken(vertx)
      client.get("/student/me")
        .send {
          resultCheck(testContext, it)
          val response = it.result()
          testContext.verify {
            assertEquals(ok, response.statusCode())
            val body = response.bodyAsJsonObject()
            assertTrue(body.containsKey("info"))
            assertTrue(body.containsKey("students"))
            testContext.completeNow()
          }
        }
    }
  }
}
