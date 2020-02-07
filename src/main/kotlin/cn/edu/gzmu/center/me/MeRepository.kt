package cn.edu.gzmu.center.me

import cn.edu.gzmu.center.model.entity.Student
import cn.edu.gzmu.center.model.entity.Teacher
import cn.edu.gzmu.center.model.extension.toJsonObject
import cn.edu.gzmu.center.model.extension.toTypeArray
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonArrayOf
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.sqlclient.preparedQueryAwait
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午5:48
 */
interface MeRepository {

  /**
   * Get current user routes by roles.
   * Roles come from [message] body.
   */
  fun roleRoutes(message: Message<JsonArray>)

  /**
   * Get current user menu by roles.
   * Roles come from [message] body.
   */
  fun roleMenu(message: Message<JsonArray>)

  /**
   * Get current user info by entity type.
   * Roles come from [message] body.
   * It has two key - value
   * - student: Boolean
   * - teacher: Boolean
   * - username: String
   */
  suspend fun meInfo(message: Message<JsonObject>)

}

class MeRepositoryImpl(private val connection: SqlConnection) : MeRepository {
  private val log: Logger = LoggerFactory.getLogger(MeRepositoryImpl::class.java.name)

  companion object {
    val ROLE_ROUTES = """
      SELECT acr.name
      FROM auth_center_res acr
               LEFT JOIN
           auth_center_role_res acrr on acr.id = acrr.res_id
               LEFT JOIN sys_role sr on sr.id = acrr.role_id
      WHERE acr.name IS NOT NULL AND acr.is_enable = true
        AND ( sr.name = any ($1) OR sr.name = 'ROLE_PUBLIC')
    """.trimIndent()
    val ROLE_MENU = """
      SELECT acr.name, acr.url, acr.method, acr.remark
      FROM auth_center_res acr
               LEFT JOIN
           auth_center_role_res acrr on acr.id = acrr.res_id
               LEFT JOIN sys_role sr on sr.id = acrr.role_id
      WHERE acr.name IS NOT NULL AND acr.url IS NOT NULL
         AND acr.method IS NOT NULL AND acr.is_enable = true
         AND ( sr.name = any ($1) OR sr.name = 'ROLE_PUBLIC')
    """.trimIndent()
    const val STUDENT_BY_USER = "SELECT * FROM student WHERE user_id = $1 AND is_enable = true"
    const val TEACHER_BY_USER = "SELECT * FROM teacher WHERE user_id = $1 AND is_enable = true"
    const val USER_BY_NAME = "SELECT u.id FROM sys_user u WHERE u.name = $1 AND u.is_enable = true"
  }

  override fun roleRoutes(message: Message<JsonArray>) {
    connection.preparedQuery(ROLE_ROUTES, Tuple.of(message.body().toTypeArray<String>())) {
      if (it.failed()) {
        message.fail(500, it.cause().message)
        throw it.cause()
      }
      val result = jsonObjectOf()
      it.result().map { res -> res.getString("name") }.forEach { res -> result.put(res, true) }
      log.debug("Success get role routes: {}", result)
      message.reply(result)
    }
  }

  override fun roleMenu(message: Message<JsonArray>) {
    connection.preparedQuery(ROLE_MENU, Tuple.of(message.body().toTypeArray<String>())) {
      if (it.failed()) {
        message.fail(500, it.cause().message)
        throw it.cause()
      }
      val result = jsonArrayOf()
      it.result().map { menu ->
        jsonObjectOf(
          "to" to jsonObjectOf("name" to menu.getString("name")),
          "text" to menu.getString("url"),
          "icon" to menu.getString("method"),
          "remark" to (menu.getString("remark") ?: "default")
        )
      }.groupBy { json -> json.getString("remark") }
        .forEach { (key, value) ->
          result.add(
            jsonObjectOf(
              "name" to key,
              "children" to value
            )
          )
        }
      log.debug("Success get role menu: {}", result)
      message.reply(jsonObjectOf("menus" to result))
    }
  }

  override suspend fun meInfo(message: Message<JsonObject>) {
    val body = message.body()
    val student = body.getBoolean("student")
    val teacher = body.getBoolean("teacher")
    val username = body.getString("username")
    try {
      val rows = connection.preparedQueryAwait(USER_BY_NAME, Tuple.of(username))
      if (rows.rowCount() == 0) message.reply(JsonObject())
      val tuple = Tuple.of(rows.first().getLong("id"))
      val result = when {
        student -> studentInfo(tuple)
        teacher -> teacherInfo(tuple)
        else -> JsonObject()
      }
      log.debug("Success get user info: {}", result)
      message.reply(result)
    } catch (e: Exception) {
      message.fail(500, e.localizedMessage)
      throw e
    }
  }

  @Throws(Exception::class)
  private suspend fun studentInfo(userId: Tuple): JsonObject {
    val rowSet = connection.preparedQueryAwait(STUDENT_BY_USER, userId)
    return if (rowSet.size() == 0) JsonObject()
    else rowSet.first().toJsonObject<Student>()
  }

  @Throws(Exception::class)
  private suspend fun teacherInfo(userId: Tuple): JsonObject {
    val rowSet = connection.preparedQueryAwait(TEACHER_BY_USER, userId)
    return if (rowSet.size() == 0) JsonObject()
    else rowSet.first().toJsonObject<Teacher>()
  }


}
