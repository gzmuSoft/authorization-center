package cn.edu.gzmu.center.config

import cn.edu.gzmu.center.model.extension.database
import cn.edu.gzmu.center.model.extension.databaseInt
import cn.edu.gzmu.center.model.extension.oauth
import cn.edu.gzmu.center.oauth.*
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.pgclient.PgConnectOptions
import io.vertx.sqlclient.PoolOptions

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/23 上午10:32
 */
class ApplicationConfig(private val vertx: Vertx) {

  /**
   * Watch config file.
   *
   * @return JsonObject result
   */
  internal suspend fun config(): JsonObject {
    val store =
      ConfigStoreOptions()
        .setType("file")
        .setFormat("yaml")
        .setConfig(JsonObject().put("path", "application.yml"))
    val retrieverOptions = ConfigRetrieverOptions()
      .setScanPeriod(2000)
      .addStore(store)
    return ConfigRetriever.create(vertx, retrieverOptions).getConfigAwait()
  }


  /**
   * Oauth config options.
   *
   * @return OAuth2ClientOptions
   */
  internal suspend fun oauthConfig(): OAuth2ClientOptions{
    val config = config()
    return OAuth2ClientOptions(
      jsonObjectOf(
        "clientID" to config.oauth(CLIENT_ID),
        "clientSecret" to config.oauth(CLIENT_SECRET),
        "site" to config.oauth(SERVER),
        "flow" to OAuth2FlowType.AUTH_CODE,
        "tokenPath" to config.oauth(TOKEN),
        "authorizationPath" to config.oauth(AUTHORIZATION),
        "introspectionPath" to config.oauth(TOKEN_INFO),
        "scopeSeparator" to ","
      )
    )
  }


  internal suspend fun databaseConfig(): PgConnectOptions {
    val config = config()
    return PgConnectOptions(
      jsonObjectOf(
        "port" to config.databaseInt("port", 5432),
        "host" to config.database("host", "127.0.0.1"),
        "database" to config.database("database", "public"),
        "user" to config.database("user", "postgres"),
        "password" to config.database("password", "postgres")
      )
    )
  }

  internal suspend fun poolConfig(): PoolOptions  {
    val config = config()
    return PoolOptions(jsonObjectOf(
      "maxSize" to config.databaseInt("maxSize", 5),
      "maxWaitQueueSize" to config.databaseInt("maxWaitQueueSize", -1)
    ))
  }

}

