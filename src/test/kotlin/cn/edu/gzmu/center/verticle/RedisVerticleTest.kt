package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.Config
import cn.edu.gzmu.center.model.address.API_INFO
import cn.edu.gzmu.center.model.address.GET_API_INFO
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/5/17 上午7:30
 */
@ExtendWith(VertxExtension::class)
class RedisVerticleTest {

  @BeforeEach
  fun deploy(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(RedisVerticle(), DeploymentOptions().setConfig(Config.redis))
      .handler = testContext.succeeding { testContext.completeNow() }
  }

  @Test
  internal fun `add api number test`(vertx: Vertx, testContext: VertxTestContext) {
    val eventBus = vertx.eventBus()
    eventBus.request<String>(GET_API_INFO, "/oauth")
      .onSuccess {
        print(it.body())
        testContext.completeNow()
      }
      .onFailure {
        print(it.cause)
        testContext.failNow(it)
      }
  }
}
