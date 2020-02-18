package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.entity.SysUser
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.model.extension.toJsonObject
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.sqlclient.getConnectionAwait
import io.vertx.kotlin.sqlclient.preparedQueryAwait
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.*

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/17 下午10:54
 */
interface UserRepository {
  /**
   * Get user by Id
   */
  fun userOne(message: Message<Long>)

  /**
   * Get user by Id
   */
  suspend fun userPassword(message: Message<JsonObject>)

  /**
   * Update user
   */
  fun userUpdate(message: Message<JsonObject>)
}

class UserRepositoryImpl(private val pool: PgPool) : BaseRepository(), UserRepository {
  private val log: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java.name)
  private val table = "sys_user"

  companion object {
    const val USER_UPDATE =
      "UPDATE sys_user SET name = $1, email = $2, phone = $3, modify_time = $4, modify_user = $5 WHERE id = $6"
  }

  override fun userOne(message: Message<Long>) {
    pool.preparedQuery("SELECT * FROM $table WHERE id = $1", Tuple.of(message.body())) {
      messageException(message, it)
      if (it.result().count() == 0) {
        message.fail(404, "暂无此用户信息")
        return@preparedQuery
      }
      val user = it.result().first().toJsonObject<SysUser>()
      log.debug("Success get user: {}", user)
      user.remove("password")
      message.reply(user)
    }
  }

  override suspend fun userPassword(message: Message<JsonObject>) {
    val body = message.body()
    val studentId = body.getLong("id")
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val studentRowSet =
        connection.preparedQueryAwait("SELECT no, user_id FROM student WHERE id = $1", Tuple.of(studentId))
      if (studentRowSet.count() == 0) {
        message.fail(404, "暂无此学生信息")
        return
      }
      val no = studentRowSet.first().getString("no")
      val userId = studentRowSet.first().getLong("user_id")
      if (Objects.isNull(no) || no.length < 6) {
        message.fail(404, "此学生学号不存在或小于 6 位")
        return
      }
      val password = BCrypt.hashpw(no.substring(no.length - 6), BCrypt.gensalt(Address.LOG_ROUNDS))
      connection.preparedQueryAwait("UPDATE $table SET password = $1 WHERE id = $2", Tuple.of(password, userId))
      log.debug("Success reset user {} password: {}", userId, no.substring(no.length - 6))
      message.reply("Success")
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    } finally {
      connection?.close()
      log.debug("Close temporary database connection.")
    }
  }

  override fun userUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val user = body.mapAs(SysUser.serializer())
    pool.preparedQuery(
      USER_UPDATE,
      Tuple.of(user.name, user.email, user.phone, user.modifyTime, user.modifyUser, user.id)
    ) {
      messageException(message, it)
      log.debug("Success update user info: {}", user)
      message.reply("Success")
    }
  }
}
