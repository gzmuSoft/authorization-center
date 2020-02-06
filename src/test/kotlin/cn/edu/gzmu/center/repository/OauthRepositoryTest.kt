package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.DatabaseHelper
import cn.edu.gzmu.center.oauth.Oauth
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonArrayOf
import org.junit.jupiter.api.Test

/**
 * Oauth repository test.
 * Only use [eventBus] to test all address.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午5:53
 */
internal class OauthRepositoryTest : DatabaseHelper() {

  @Test
  fun `Role Resource Test`(testContext: VertxTestContext) {
    eventBus.request<JsonObject>(Oauth.ADDRESS_ROLE_RESOURCE, jsonArrayOf("ROLE_ADMIN")) {
      if (it.failed()) testContext.failNow(it.cause())
      else {
        val body = it.result().body()
        body.forEach(::println)
        testContext.completeNow()
      }
    }
  }

}
