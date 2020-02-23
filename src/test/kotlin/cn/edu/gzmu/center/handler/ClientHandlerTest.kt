package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/23 下午8:21
 */
class ClientHandlerTest : OauthHelper() {

  @Test
  internal fun `Get clients when passed`(testContext: VertxTestContext) {
    client.get("/client")
      .send {
        okCheck(testContext, it)
      }
  }
}
