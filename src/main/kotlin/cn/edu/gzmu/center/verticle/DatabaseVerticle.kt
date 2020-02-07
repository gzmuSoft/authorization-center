package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ME_INFO
import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ROLE_ROUTES
import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ROLE_MENU
import cn.edu.gzmu.center.me.MeRepository
import cn.edu.gzmu.center.me.MeRepositoryImpl
import cn.edu.gzmu.center.oauth.Oauth
import cn.edu.gzmu.center.oauth.Oauth.Companion.ADDRESS_ROLE_RESOURCE
import cn.edu.gzmu.center.oauth.OauthRepository
import cn.edu.gzmu.center.oauth.OauthRepositoryImpl
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.sqlclient.getConnectionAwait
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions
import io.vertx.sqlclient.SqlConnection
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
  private lateinit var connection: SqlConnection

  /**
   * Will get config from application.conf and start database connection.
   */
  override suspend fun start() {
    try {
      val client = PgPool.pool(vertx, databaseConfig(), poolConfig())
      connection = client.getConnectionAwait()
      meHandles()
      log.info("Success start database verticle......")
      vertx.exceptionHandler {
        log.error("Get a exception")
        throw it
      }
    } catch (e: Exception) {
      log.error("Failed start database verticle!", e.cause)
    }
  }

  private fun meHandles() {
    val eventBus = vertx.eventBus()
    val oauthRepository: OauthRepository = OauthRepositoryImpl(connection)
    eventBus.localConsumer<JsonArray>(ADDRESS_ROLE_RESOURCE, oauthRepository::roleResource)
    eventBus.localConsumer<String>(Oauth.ADDRESS_ME, oauthRepository::me)
    val meRepository: MeRepository = MeRepositoryImpl(connection)
    eventBus.localConsumer<JsonArray>(ADDRESS_ROLE_ROUTES, meRepository::roleRoutes)
    eventBus.localConsumer<JsonArray>(ADDRESS_ROLE_MENU, meRepository::roleMenu)
    eventBus.localConsumer<JsonObject>(ADDRESS_ME_INFO) {
      launch { meRepository.meInfo(it) }
    }
  }

  private fun databaseConfig(): PgConnectOptions =
    PgConnectOptions(
      jsonObjectOf(
        "port" to config.getInteger("port", 5432),
        "host" to config.getString("host", "127.0.0.1"),
        "database" to config.getString("database", "public"),
        "user" to config.getString("user", "postgres"),
        "password" to config.getString("password", "postgres")
      )
    ).addProperty("search_path", config.getString("schema", "public"))

  private fun poolConfig(): PoolOptions =
    PoolOptions(
      jsonObjectOf(
        "maxSize" to config.getInteger("maxSize", 5),
        "maxWaitQueueSize" to config.getInteger("maxWaitQueueSize", -1)
      )
    )

  override suspend fun stop() {
    connection.close()
  }
}
