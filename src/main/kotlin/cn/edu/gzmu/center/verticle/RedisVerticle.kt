package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.model.address.API_INFO
import cn.edu.gzmu.center.model.address.API_NUMBER
import cn.edu.gzmu.center.model.address.API_URL
import cn.edu.gzmu.center.model.address.GET_API_INFO
import io.vertx.core.CompositeFuture
import io.vertx.core.eventbus.Message
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.redis.client.*
import io.vertx.redis.client.Redis
import io.vertx.redis.client.RedisAPI
import io.vertx.redis.client.Response
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/5/16 下午9:11
 */
class RedisVerticle : CoroutineVerticle() {
  private lateinit var redisAPI: RedisAPI
  private val log: Logger = LoggerFactory.getLogger(RedisVerticle::class.java.name)
  override suspend fun start() {
    try {
      val eventBus = vertx.eventBus()
      val redisConnection = Redis.createClient(vertx, config.getString("url")).connectAwait()
      redisAPI = RedisAPI.api(redisConnection)
      log.info("Success start redis connection.")
      eventBus.localConsumer<String>(API_INFO) { launch { this@RedisVerticle.updateApiInfo(it) } }
      eventBus.localConsumer(GET_API_INFO, ::getApiInfo)
    } catch (e: Exception) {
      log.error("Failed start redis connection: {}", e.localizedMessage)
    }
  }

  private suspend fun updateApiInfo(message: Message<String>) {
    try {
      val apiNumber = redisAPI.getAwait(API_NUMBER)
      val number = apiNumber?.toLong() ?: 0
      redisAPI.setAwait(listOf(API_NUMBER, "${number + 1}"))
      val url = message.body()
      redisAPI.lpushAwait(listOf(API_URL, url))
      message.reply("Success")
    } catch (e: Exception) {
      message.reply(e.localizedMessage)
    }
  }

  private fun getApiInfo(message: Message<Unit>) {
    val apiNumberFuture = redisAPI.get(API_NUMBER)
    val authorizationServerApiUrlFuture = redisAPI.llen("authorization_server_api_url")
    CompositeFuture.all(apiNumberFuture, authorizationServerApiUrlFuture)
      .onComplete { ar ->
        if (ar.succeeded()) {
          val future = ar.result()
          val apiNumber = future.resultAt<Response>(0).toLong()
          val authorizationServerApiUrl = future.resultAt<Response>(1).toLong()
          message.reply(
            jsonObjectOf(
              "apiNumber" to apiNumber,
              "authorizationServerApiUrl" to authorizationServerApiUrl
            )
          )
        } else {
          message.fail(500, ar.cause().localizedMessage)
        }
      }
  }
}


