package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.config.ApplicationConfig
import io.vertx.core.Promise
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.sqlclient.getConnectionAwait
import io.vertx.kotlin.sqlclient.queryAwait
import io.vertx.pgclient.PgPool
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Database Verticle.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/1 下午6:22
 */
class DatabaseVerticle : CoroutineVerticle() {

  private val log: Logger = LoggerFactory.getLogger(DatabaseVerticle::class.java.name)

  override suspend fun start() {
    val config = ApplicationConfig(vertx)
    val connectOptions = config.databaseConfig()
    val poolOptions = config.poolConfig()
    val client = PgPool.pool(vertx, connectOptions, poolOptions)
    try {
      val connection = client.getConnectionAwait()
      log.info("Success start database verticle......")
    } catch (e: Exception) {
      log.error("Failed start database verticle!", e.cause)
    }
  }
}
