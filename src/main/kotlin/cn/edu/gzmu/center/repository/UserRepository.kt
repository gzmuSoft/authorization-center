package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.entity.SysRole
import cn.edu.gzmu.center.model.entity.SysUser
import cn.edu.gzmu.center.model.extension.encodePassword
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.model.extension.toJsonObject
import cn.edu.gzmu.center.model.extension.toTypeArray
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.sqlclient.*
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple
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
  suspend fun userOne(message: Message<Long>)

  /**
   * Get user by Id
   */
  suspend fun userPassword(message: Message<JsonObject>)

  /**
   * Update user
   */
  suspend fun userUpdate(message: Message<JsonObject>)

  /**
   * user exist
   * [message] are user names
   */
  fun userExist(message: Message<JsonArray>)
}

class UserRepositoryImpl(private val pool: PgPool) : BaseRepository(pool), UserRepository {
  private val log: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java.name)
  private val table = "sys_user"

  companion object {
    const val USER_UPDATE =
      "UPDATE sys_user SET name = $1, email = $2, phone = $3, modify_time = $4, modify_user = $5 WHERE id = $6"
    const val USER_EXIST =
      "SELECT id, name FROM sys_user WHERE name = any ($1)"
    private val USER_ONE_ROLE = """
      SELECT sr.*
      FROM sys_user_role sur
      LEFT JOIN sys_role sr on sur.role_id = sr.id
      WHERE sur.is_enable = true
      AND sr.is_enable = true
      AND sur.user_id = $1
    """.trimIndent()
    private const val USER_ROLE_DELETE =
      "UPDATE sys_user_role SET is_enable = false WHERE user_id = $1 AND role_id = $2"
    private const val USER_ROLE_ADD =
      "INSERT INTO sys_user_role(user_id, role_id) VALUES ($1, $2)"
  }

  override suspend fun userOne(message: Message<Long>) {
    val userId = message.body()
    try {
      val userRow = pool.preparedQueryAwait("SELECT * FROM $table WHERE id = $1", Tuple.of(userId))
      if (userRow.count() == 0) {
        message.fail(404, "暂无此用户信息")
        return
      }
      val user = userRow.first().toJsonObject<SysUser>()
      val roles = pool.preparedQueryAwait(USER_ONE_ROLE, Tuple.of(userId)).map { it.toJsonObject<SysRole>() }
      user.remove("password")
      user.put("roles", roles)
      user.put("roleIds", roles.map { it.getLong("id") })
      log.debug("Success get user: {}", user.getString("name"))
      message.reply(user)
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
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
      val password = encodePassword(no)
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

  override suspend fun userUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val user = body.mapAs(SysUser.serializer())
    val transaction = pool.beginAwait()
    try {
      transaction.preparedQueryAwait(
        USER_UPDATE,
        Tuple.of(user.name, user.email, user.phone, user.modifyTime, user.modifyUser, user.id)
      )
      // See cn.edu.gzmu.center.other.JsonTest.listTest
      val roles = body.getJsonArray("roleIds").toList()
      val existRoles = transaction.preparedQueryAwait(USER_ONE_ROLE, Tuple.of(user.id))
        .map { it.getLong("id") }
      // Delete user role association
      val deleteParams = existRoles.subtract(roles).map { Tuple.of(user.id, it) }
      transaction.preparedBatchAwait(USER_ROLE_DELETE, deleteParams)
      // Add user role association
      val addParams = roles.subtract(existRoles).map { Tuple.of(user.id, it) }
      transaction.preparedBatchAwait(USER_ROLE_ADD, addParams)
      transaction.commitAwait()
      message.reply("Success")
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    }
  }

  override fun userExist(message: Message<JsonArray>) {
    pool.preparedQuery(USER_EXIST, Tuple.of(message.body().toTypeArray<String>())) {
      messageException(message, it)
      val result = it.result().map { row -> row.toJsonObject<SysUser>() }
      log.debug("Success exist user: {}", result)
      message.reply(JsonArray(result))
    }
  }
}
