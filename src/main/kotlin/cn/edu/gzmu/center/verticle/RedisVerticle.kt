package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.model.address.*
import io.vertx.core.CompositeFuture
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
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
      eventBus.localConsumer<Unit>(DASHBOARD_DATE_INFO) { launch { this@RedisVerticle.getDateInfo(it) } }
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

  private fun getApiInfo(message: Message<String>) {
    val username = message.body()
    CompositeFuture.all(
      listOf(
        redisAPI.get(API_NUMBER), redisAPI.llen("authorization_server_api_url"),
        redisAPI.get("success:$username"), redisAPI.get("failure:$username"),
        redisAPI.get("login_success_api_number"), redisAPI.get("login_failure_api_number")
      )
    )
      .onComplete { ar ->
        if (ar.succeeded()) {
          val future = ar.result()
          message.reply(
            jsonObjectOf(
              "apiNumber" to future.resultAt<Response>(0).toSafeLong(),
              "authorizationServerApiNumber" to future.resultAt<Response>(1).toSafeLong(),
              "userSuccessLogin" to future.resultAt<Response>(2).toSafeLong(),
              "userFailureLogin" to future.resultAt<Response>(3).toSafeLong(),
              "loginSuccess" to future.resultAt<Response>(4).toSafeLong(),
              "loginFailure" to future.resultAt<Response>(5).toSafeLong()
            )
          )
        } else {
          message.fail(500, ar.cause().localizedMessage)
        }
      }
  }

  private suspend fun getDateInfo(message: Message<Unit>) {
    try {
      message.reply(
        jsonObjectOf(
          "dateApi" to keyNumber("authorization_server_data_api_number"),
          "loginDateApi" to keyNumber("authorization_server_data_login_api_number")
        )
      )
    } catch (e: Exception) {
      message.fail(500, e.localizedMessage)
      throw e
    }
  }

  private suspend fun keyNumber(key: String): JsonObject {
    var list = redisAPI.keysAwait("$key*")?.toList() ?: emptyList()
    list = list.sortedByDescending { it.toString() }
    if (list.size > 7) list = list.subList(0, 7)
    return JsonObject(list.associateBy(
      { it.toString().substringAfter("$key=") },
      { redisAPI.getAwait(it.toString()).toSafeLong() }
    ))
  }
}

fun Response?.toSafeLong() = this?.toLong() ?: 0

