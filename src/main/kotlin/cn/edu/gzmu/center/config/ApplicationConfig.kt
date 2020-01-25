package cn.edu.gzmu.center.config

import cn.edu.gzmu.center.oauth.*
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.PubSecKeyOptions
import io.vertx.ext.auth.jwt.JWTAuthOptions
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.core.file.readFileAwait

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

  internal suspend fun jwtConfig(): JWTAuthOptions =
    JWTAuthOptions().addPubSecKey(
      PubSecKeyOptions()
        .setBuffer(vertx.fileSystem().readFileAwait("public.txt").toString())
        .setAlgorithm("RS256")
    )

  /**
   * Oauth config options.
   *
   * @return OAuth2ClientOptions
   */
  internal suspend fun oauthConfig(): OAuth2ClientOptions =
    OAuth2ClientOptions(
      JsonObject()
        .put("clientID", config().oauth(CLIENT_ID))
        .put("clientSecret", config().oauth(CLIENT_SECRET))
        .put("site", config().oauth(SERVER))
        .put("flow", OAuth2FlowType.AUTH_CODE)
        .put("tokenPath", config().oauth(TOKEN))
        .put("authorizationPath", config().oauth(AUTHORIZATION))
        .put("introspectionPath", config().oauth(TOKEN_INFO))
        .put("scopeSeparator", ",")
    )


}

