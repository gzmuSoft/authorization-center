package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.Student
import cn.edu.gzmu.center.model.entity.Teacher
import cn.edu.gzmu.center.model.extension.*
import cn.edu.gzmu.center.model.address.Address.Companion.LOG_ROUNDS
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonArrayOf
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.sqlclient.preparedQueryAwait
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.time.LocalDateTime

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
   * - id: Long
   */
  suspend fun meInfo(message: Message<JsonObject>)

  /**
   * Update current user.
   * User come from [message] body.
   * - userId: Long user Id
   * - username: String
   * - name: String
   * - email: String
   * - phone: String
   * - password: String
   */
  fun meUser(message: Message<JsonObject>)

  /**
   * Update current user info.
   */
  fun meInfoUpdate(message: Message<JsonObject>)
}

class MeRepositoryImpl(private val pool: PgPool) : BaseRepository(),
  MeRepository {
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
      ORDER BY acr.sort
    """.trimIndent()
    const val STUDENT_BY_USER = "SELECT * FROM student WHERE user_id = $1 AND is_enable = true"
    const val TEACHER_BY_USER = "SELECT * FROM teacher WHERE user_id = $1 AND is_enable = true"
  }

  override fun roleRoutes(message: Message<JsonArray>) {
    pool.preparedQuery(ROLE_ROUTES, Tuple.of(message.body().toTypeArray<String>())) {
      messageException(message, it)
      val result = jsonObjectOf()
      it.result().map { res -> res.getString("name") }.forEach { res -> result.put(res, true) }
      log.debug("Success get role routes: {}", result)
      message.reply(result)
    }
  }

  override fun roleMenu(message: Message<JsonArray>) {
    pool.preparedQuery(ROLE_MENU, Tuple.of(message.body().toTypeArray<String>())) {
      messageException(message, it)
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
    val id = body.getLong("id")
    try {
      val tuple = Tuple.of(id)
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

  override fun meUser(message: Message<JsonObject>) {
    val body = message.body()
    val password = body.getString("password") ?: ""
    val sql = """
      UPDATE sys_user SET email = $1, phone = $2,
             modify_user = $4, modify_time = $5
          ${if (password.isBlank()) "" else ", password = $6"}
      WHERE id = $3 AND is_enable = true
    """.trimIndent()
    val tuple = Tuple.of(
      body.getString("email"), body.getString("phone"),
      body.getLong("userId"), body.getString("username"), LocalDateTime.now()
    )
    if (!password.isBlank()) tuple.addString(BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS)))
    pool.preparedQuery(sql, tuple) {
      messageException(message, it)
      log.debug("Success update user info: ", body)
      message.reply("success")
    }
  }

  override fun meInfoUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val pair =
      if (body.getBoolean("student")) studentUpdate(body.mapAs(Student.serializer()))
      else teacherUpdate(body.mapAs(Teacher.serializer()))
    println(pair.first)
    pool.preparedQuery(pair.first, pair.second) {
      if (it.failed()) messageException(message, it)
      log.debug("Success update user info: ", body)
      message.reply("success")
    }
  }

  private fun studentUpdate(student: Student): Pair<String, Tuple> {
    val sql = Sql("student")
    sql.update()
      .set { "name" }.setIf(student::no).setIf(student::gender).setIf(student::nation)
      .setIf(student::idNumber).setIf(student::birthday).setIf(student::academic)
      .setIf(student::graduationDate).setIf(student::graduateInstitution).setIf(student::originalMajor)
      .setIf(student::resume).setIf(student::modifyTime).setIf(student::modifyUser).where("user_id")
    val tuple = Tuple.of(student.name)
      .addOptional(student.no).addOptional(student.gender).addOptional(student.nation)
      .addOptional(student.idNumber).addOptional(student.birthday).addOptional(student.academic)
      .addOptional(student.graduationDate).addOptional(student.graduateInstitution).addOptional(student.originalMajor)
      .addOptional(student.resume).addOptional(student.modifyTime).addOptional(student.modifyUser)
      .addOptional(student.userId)
    return Pair(sql.get(), tuple)
  }

  private fun teacherUpdate(teacher: Teacher): Pair<String, Tuple> {
    val sql = Sql("teacher")
    sql.update()
      .set { "name" }.setIf(teacher::gender).setIf(teacher::idNumber).setIf(teacher::birthday)
      .setIf(teacher::nation).setIf(teacher::degree).setIf(teacher::academic)
      .setIf(teacher::graduationDate).setIf(teacher::graduateInstitution).setIf(teacher::major)
      .setIf(teacher::majorResearch).setIf(teacher::workDate).setIf(teacher::profTitle)
      .setIf(teacher::profTitleAssDate).setIf(teacher::subjectCategory).setIf(teacher::isAcademicLeader)
      .setIf(teacher::resume).setIf(teacher::modifyTime).setIf(teacher::modifyUser)
      .where("user_id")
    val tuple = Tuple.of(teacher.name)
      .addOptional(teacher.gender).addOptional(teacher.idNumber).addOptional(teacher.birthday)
      .addOptional(teacher.nation).addOptional(teacher.degree).addOptional(teacher.academic)
      .addOptional(teacher.graduationDate).addOptional(teacher.graduateInstitution).addOptional(teacher.major)
      .addOptional(teacher.majorResearch).addOptional(teacher.workDate).addOptional(teacher.profTitle)
      .addOptional(teacher.profTitleAssDate).addOptional(teacher.subjectCategory).addOptional(teacher.isAcademicLeader)
      .addOptional(teacher.resume).addOptional(teacher.modifyTime).addOptional(teacher.modifyUser)
      .addOptional(teacher.userId)
    return Pair(sql.get(), tuple)
  }


  @Throws(Exception::class)
  private suspend fun studentInfo(userId: Tuple): JsonObject {
    val rowSet = pool.preparedQueryAwait(STUDENT_BY_USER, userId)
    return if (rowSet.size() == 0) JsonObject()
    else rowSet.first().toJsonObject<Student>()
  }

  @Throws(Exception::class)
  private suspend fun teacherInfo(userId: Tuple): JsonObject {
    val rowSet = pool.preparedQueryAwait(TEACHER_BY_USER, userId)
    return if (rowSet.size() == 0) JsonObject()
    else rowSet.first().toJsonObject<Teacher>()
  }


}
