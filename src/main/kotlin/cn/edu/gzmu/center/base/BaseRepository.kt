package cn.edu.gzmu.center.base

import cn.edu.gzmu.center.model.entity.Student
import cn.edu.gzmu.center.model.extension.encodePassword
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.repository.StudentRepositoryImpl
import io.vertx.core.AsyncResult
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.awaitBlocking
import io.vertx.kotlin.sqlclient.beginAwait
import io.vertx.kotlin.sqlclient.commitAwait
import io.vertx.kotlin.sqlclient.preparedBatchAwait
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import java.lang.Exception
import java.time.LocalDateTime

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/10 下午8:40
 */
abstract class BaseRepository(private val pool: PgPool) {

  @Throws(Exception::class)
  fun <T> messageException(message: Message<T>, ar: AsyncResult<*>) {
    if (ar.failed()) {
      message.fail(500, ar.cause().message)
      throw ar.cause()
    }
  }

  @Throws(Exception::class)
  fun <T> messageException(message: Message<T>, ar: Throwable) {
    message.fail(500, ar.message)
    throw ar
  }

  protected suspend fun import(
    message: Message<JsonObject>,
    nothing: String,
    update: String,
    params: (content: JsonArray, createUser: String) -> List<Tuple>
  ) {
    val body = message.body()
    val content = body.getJsonArray("content")
    val config = body.getJsonObject("config", JsonObject())
    val createUser = body.getString("createUser")
    val transaction = pool.beginAwait()
    try {
      val param = awaitBlocking { params(content, createUser) }
      transaction.preparedBatchAwait(
        if (config.getBoolean("update", false)) update else nothing,
        param
      )
      transaction.commitAwait()
      message.reply("Success")
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    }
  }

}
