package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.model.address.*
import cn.edu.gzmu.center.model.address.Me
import cn.edu.gzmu.center.model.address.Oauth
import cn.edu.gzmu.center.repository.*
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
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
  private lateinit var pool: PgPool
  private lateinit var eventBus: EventBus

  /**
   * Will get config from application.conf and start database connection.
   */
  override suspend fun start() {
    try {
      pool = PgPool.pool(vertx, databaseConfig(), poolConfig())
      eventBus = vertx.eventBus()
      meRepository()
      baseRepository()
      systemRepository()
      dataRepository()
      log.info("Success start database verticle......")
      vertx.exceptionHandler {
        it.printStackTrace()
      }
    } catch (e: Exception) {
      log.error("Failed start database verticle!", e.cause)
    }
  }

  private fun dataRepository() {
    val dataRepository: DataRepository = DataRepositoryImpl(pool)
    eventBus.localConsumer<Long>(Data.ADDRESS_DATA_TYPE, dataRepository::dataType)
    eventBus.localConsumer<Long>(Data.ADDRESS_DATA_PARENT, dataRepository::dataParent)
    eventBus.localConsumer<Long>(Data.ADDRESS_DATA_DELETE, dataRepository::dataDelete)
    eventBus.localConsumer<JsonObject>(Data.ADDRESS_DATA_UPDATE, dataRepository::dataUpdate)
    eventBus.localConsumer<JsonObject>(Data.ADDRESS_DATA_CREATE, dataRepository::dataCreate)
    eventBus.localConsumer<JsonObject>(Data.ADDRESS_DATA_PAGE) { launch { dataRepository.dataPage(it) }}
  }

  private fun systemRepository() {
    val roleRepository: RoleRepository = RoleRepositoryImpl(pool)
    eventBus.localConsumer<Long>(Role.ADDRESS_ROLE_PARENT, roleRepository::roleParent)
    eventBus.localConsumer<Long>(Role.ADDRESS_ROLE_RES, roleRepository::roleRes)
    eventBus.localConsumer<JsonObject>(Role.ADDRESS_ROLE_UPDATE, roleRepository::roleUpdate)
    val resRepository: ResRepository = ResRepositoryIImpl(pool)
    eventBus.localConsumer<JsonObject>(Res.ADDRESS_RES) { launch { resRepository.res(it) } }
    eventBus.localConsumer<JsonObject>(Res.ADDRESS_RES_UPDATE, resRepository::resUpdate)
    eventBus.localConsumer<Long>(Res.ADDRESS_RES_DELETE, resRepository::resDelete)
    eventBus.localConsumer<JsonObject>(Res.ADDRESS_RES_CREATE, resRepository::resCreate)
  }

  private fun baseRepository() {
    val everyRepository: EveryRepository = EveryRepositoryImpl(pool)
    eventBus.localConsumer<Long>(Every.ADDRESS_SYS_DATA_TYPE, everyRepository::dataType)
    eventBus.localConsumer<JsonArray>(Every.ADDRESS_SYS_DATA_TYPES, everyRepository::dataTypes)
    eventBus.localConsumer<Long>(Every.ADDRESS_SYS_DATA_NAME, everyRepository::dataInfo)
    eventBus.localConsumer<JsonObject>(Every.ADDRESS_SYS_USER_EXIST, everyRepository::userExist)
  }

  private fun meRepository() {
    val oauthRepository: OauthRepository = OauthRepositoryImpl(pool)
    val meRepository: MeRepository = MeRepositoryImpl(pool)
    eventBus.localConsumer<JsonArray>(Oauth.ADDRESS_ROLE_RESOURCE, oauthRepository::roleResource)
    eventBus.localConsumer<String>(Oauth.ADDRESS_ME, oauthRepository::me)
    eventBus.localConsumer<JsonArray>(Me.ADDRESS_ROLE_ROUTES, meRepository::roleRoutes)
    eventBus.localConsumer<JsonArray>(Me.ADDRESS_ROLE_MENU, meRepository::roleMenu)
    eventBus.localConsumer<JsonObject>(Me.ADDRESS_ME_INFO) { launch { meRepository.meInfo(it) } }
    eventBus.localConsumer<JsonObject>(Me.ADDRESS_ME_USER, meRepository::meUser)
    eventBus.localConsumer<JsonObject>(Me.ADDRESS_ME_INFO_UPDATE, meRepository::meInfoUpdate)
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
    pool.close()
    log.info("Success stop database verticle......")
  }
}
