package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.config.ApplicationConfig
import cn.edu.gzmu.center.model.CONFIG_ADDRESS
import cn.edu.gzmu.center.model.DATABASE
import cn.edu.gzmu.center.model.database
import cn.edu.gzmu.center.model.databaseInt
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.eventbus.requestAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.sqlclient.getConnectionAwait
import io.vertx.kotlin.sqlclient.queryAwait
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions
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

  private fun databaseConfig(config: JsonObject): PgConnectOptions =
    PgConnectOptions(
      jsonObjectOf(
        "port" to config.getInteger("port", 5432),
        "host" to config.getString("host", "127.0.0.1"),
        "database" to config.getString("database", "public"),
        "user" to config.getString("user", "postgres"),
        "password" to config.getString("password", "postgres")
      )
    )

  private fun poolConfig(config: JsonObject): PoolOptions =
    PoolOptions(
      jsonObjectOf(
        "maxSize" to config.getInteger("maxSize", 5),
        "maxWaitQueueSize" to config.getInteger("maxWaitQueueSize", -1)
      )
    )

}
