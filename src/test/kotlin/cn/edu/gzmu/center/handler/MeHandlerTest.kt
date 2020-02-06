package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
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
      .send() {
        if (it.failed()) testContext.failNow(it.cause())
        else {
          val body = it.result().bodyAsJsonObject()
          println(body)
          testContext.completeNow()
        }
      }
  }

}
