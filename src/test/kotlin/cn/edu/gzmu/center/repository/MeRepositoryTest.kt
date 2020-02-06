package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.DatabaseHelper
import cn.edu.gzmu.center.me.Me
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonArrayOf
import org.junit.jupiter.api.Test

/**
 * User repository Test.
 * Only use [eventBus] to test all address.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午10:01
 */
internal class MeRepositoryTest : DatabaseHelper() {

  @Test
  fun `Role Routes Test`(testContext: VertxTestContext) {
    eventBus.request<JsonObject>(Me.ADDRESS_ROLE_ROUTES, jsonArrayOf("ROLE_ADMIN")) {
      if (it.failed()) testContext.failNow(it.cause())
      else {
        val body = it.result().body()
        body.forEach(::println)
        testContext.completeNow()
      }
    }
  }
}
