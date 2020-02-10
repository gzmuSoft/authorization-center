package cn.edu.gzmu.center.base

import io.vertx.core.AsyncResult
import io.vertx.core.eventbus.Message
import java.lang.Exception

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/10 下午8:40
 */
open class BaseRepository {

  @Throws(Exception::class)
  fun <T> messageException(message: Message<T>, ar: AsyncResult<*>) {
    if (ar.failed()) {
      message.fail(500, ar.cause().message)
      throw ar.cause()
    }
  }
}
