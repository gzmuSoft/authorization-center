package cn.edu.gzmu.center

import cn.edu.gzmu.center.verticle.DatabaseVerticle
import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/7 上午12:53
 */
@ExtendWith(VertxExtension::class)
open class DatabaseHelper {

  protected lateinit var eventBus: EventBus

  @BeforeEach
  fun setUp(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(DatabaseVerticle(), testContext.succeeding<String> {
      eventBus = vertx.eventBus()
      testContext.completeNow()
    })
  }
}
