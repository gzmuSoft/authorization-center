package cn.edu.gzmu.center

import cn.edu.gzmu.center.model.extension.oauth
import cn.edu.gzmu.center.verticle.DatabaseVerticle
import cn.edu.gzmu.center.verticle.WebVerticle
import io.netty.handler.codec.http.HttpHeaderNames
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.CompositeFuture
import io.vertx.core.MultiMap
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.client.WebClientSession
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.ext.web.client.sendFormAwait
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午10:15
 */
@ExtendWith(VertxExtension::class)
open class OauthHelper(private var username: String = "admin", private var password: String = "1997") {

  constructor() : this("admin", "1997")

  protected lateinit var config: JsonObject
  protected lateinit var token: String
  protected lateinit var client: WebClientSession

  init {
    val launch = GlobalScope.launch {
      val vertx = Vertx.vertx()
      config(vertx)
      oauthToken(vertx)
    }
    runBlocking {
      // wait the config
      launch.join()
    }
  }

  /**
   * Get config.
   */
  private suspend fun config(vertx: Vertx) {
    val store =
      ConfigStoreOptions()
        .setType("file")
        .setFormat("yaml")
        .setConfig(JsonObject().put("path", "application.yml"))
    val retrieverOptions = ConfigRetrieverOptions()
      .setScanPeriod(2000)
      .addStore(store)
    config = ConfigRetriever.create(vertx, retrieverOptions).getConfigAwait()
  }

  /**
   * Get token by password method.
   * Client must support password grant type.
   */
  private suspend fun oauthToken(vertx: Vertx) {
    val clientId = config.oauth("client-id")
    val clientSecret = config.oauth("client-secret")
    val secret = "Basic " + String(Base64.getEncoder().encode("$clientId:$clientSecret".toByteArray()))
    val oauthServer = WebClientSession.create(WebClient.create(vertx))
    oauthServer.addHeader(HttpHeaderNames.AUTHORIZATION.toString(), secret)
    val form: MultiMap = MultiMap.caseInsensitiveMultiMap()
    // You can change user.
    form["username"] = username
    form["password"] = password
    form["grant_type"] = "password"
    form["scope"] = config.oauth("scope")
    val response =
      oauthServer.postAbs(config.oauth("server") + config.oauth("token"))
        .sendFormAwait(form)
    val result = response.bodyAsJsonObject()
    token = result.getString("access_token")
    val options = WebClientOptions()
    options.defaultHost = "localhost"
    options.defaultPort = 9000
    client = WebClientSession.create(WebClient.create(vertx, options))
    client.addHeader(HttpHeaderNames.AUTHORIZATION.toString(), "Bearer $token")
  }

  @BeforeEach
  fun deploy(vertx: Vertx, testContext: VertxTestContext) {
    val web = vertx.deployVerticle(WebVerticle())
    val database = vertx.deployVerticle(DatabaseVerticle())
    CompositeFuture.all(web, database).handler = testContext.succeeding {
      testContext.completeNow()
    }
  }

}

