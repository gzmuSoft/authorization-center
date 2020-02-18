package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.Student
import cn.edu.gzmu.center.model.extension.addOptional
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.model.extension.toJsonObject
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.sqlclient.getConnectionAwait
import io.vertx.kotlin.sqlclient.preparedQueryAwait
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
 * @date 2020/2/14 下午8:46
 */
interface StudentRepository {
  /**
   * Get user same class students.
   * The [message] body have:
   * name -- like name
   * no   -- like no
   * userId -- current user id
   */
  suspend fun studentMe(message: Message<JsonObject>)

  /**
   * Simple update student.
   */
  fun studentUpdate(message: Message<JsonObject>)

  /**
   * Student page.
   */
  suspend fun studentPage(message: Message<JsonObject>)

  /**
   * Complete update student.
   */
  fun studentUpdateComplete(message: Message<JsonObject>)
}

class StudentRepositoryImpl(private val pool: PgPool) : BaseRepository(), StudentRepository {
  private val log: Logger = LoggerFactory.getLogger(StudentRepositoryImpl::class.java.name)
  private val table = "student"

  companion object {
    private const val STUDENT_CLASS_ID =
      "SELECT classes_id, specialty_id FROM student WHERE student.user_id = $1 AND student.is_enable = true"
    private const val STUDENT_CLASSES_DEP =
      "SELECT id, name FROM sys_data WHERE parent_id = $1 and is_enable = true"
    private val STUDENT_USER_CLASS = """
      SELECT s.*, su.image
      FROM student s
      left join sys_user su on su.id = s.user_id
      WHERE s.is_enable = true
        AND s.classes_id = $1
        AND s.name LIKE $2
        AND s.no LIKE $3
      ORDER BY s.no, s.sort
    """.trimIndent()
    private val STUDENT_UPDATE_COMPLETE = """
      UPDATE student SET name = $1, gender = $2, no = $3, id_number = $4,
      enter_date = $5, birthday = $6, academic = $7, school_id = $8,
      college_id = $9, dep_id = $10, specialty_id = $11, classes_id = $12,
      sort = $13, remark = $14, modify_time = $15, modify_user = $16 WHERE id = $17
    """.trimIndent()
    private val STUDENT_INSERT = """
      INSERT INTO student student()
    """.trimIndent()
  }

  override suspend fun studentMe(message: Message<JsonObject>) {
    val body = message.body()
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val result =
        if (body.getBoolean("student")) studentClass(body, connection)
        else studentDep(body, connection)
      log.debug("Get students")
      message.reply(result)
    } catch (e: Exception) {
      message.fail(500, e.localizedMessage)
      throw e
    } finally {
      connection?.close()
      log.debug("Close temporary database connection.")
    }
  }

  override fun studentUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val student = body.mapAs(Student.serializer())
    val sql = Sql(table).update().set { "name" }
      .setIf(student::no).setIf(student::gender).setIf(student::enterDate)
      .setIf(student::birthday).setIf(student::idNumber).setIf(student::modifyTime)
      .setIf(student::modifyUser).setIf(student::isEnable)
      .where(student::id.name).get()
    pool.preparedQuery(
      sql, Tuple.of(
        student.name, student.no, student.gender,
        student.enterDate, student.birthday, student.idNumber,
        student.modifyTime, student.modifyUser, student.isEnable, student.id
      )
    ) {
      messageException(message, it)
      log.debug("Success update user info: ", student)
      message.reply("success")
    }
  }

  override suspend fun studentPage(message: Message<JsonObject>) {
    val body = message.body()
    val sort = body.getString("sort")
    body.put("sort", 1)
    val student = body.mapAs(Student.serializer())
    val baseSql = Sql(table)
      .select(Student::class)
      .whereLike(student::name).like(student::no)
      .andNonNull(
        student::gender, student::enterDate, student::nation, student::academic,
        student::schoolId, student::collegeId, student::depId, student::specialtyId,
        student::classesId, student::isEnable
      )
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val params = Tuple.of("%${student.name}%", "%${student.no}%").addOptional(
        student.gender,
        student.enterDate, student.nation, student.academic, student.schoolId,
        student.collegeId, student.depId, student.specialtyId,
        student.classesId, student.isEnable
      )
      val count = connection.preparedQueryAwait(baseSql.count(), params).first().getLong("count")
      val sql = baseSql.page(sort).get()
      val rowSet =
        connection.preparedQueryAwait(sql, params.addLong(body.getLong("size")).addLong(body.getLong("offset")))
      val resource = body.getJsonArray("resource").map { it as JsonObject }
      val userViewPermission = resource.find { res ->
        res.getString("url") == "/user/*" && res.getString("method") == "GET"
      } != null
      val userEditPermission = resource.find { res ->
        res.getString("url") == "/user" && res.getString("method") == "PATCH"
      } != null
      val userPasswordPermission = resource.find { res ->
        res.getString("url") == "/user/password" && res.getString("method") == "PATCH"
      } != null
      val studentEditPermission = resource.find { res ->
        res.getString("url") == "/student/complete" && res.getString("method") == "PATCH"
      } != null
      val content = rowSet.map {
        it.toJsonObject<Student>()
          .put("userView", userViewPermission).put("userEdit", userEditPermission)
          .put("edit", studentEditPermission).put("resetPassword", userPasswordPermission)
      }
      log.debug("Success get page data: {}", count)
      message.reply(jsonObjectOf("content" to content, "itemsLength" to count))
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    } finally {
      connection?.close()
      log.debug("Close temporary database connection.")
    }
  }

  override fun studentUpdateComplete(message: Message<JsonObject>) {
    val body = message.body()
    val student = body.mapAs(Student.serializer())
    pool.preparedQuery(
      STUDENT_UPDATE_COMPLETE, Tuple.of(
        student.name, student.gender,
        student.no, student.idNumber, student.enterDate, student.birthday, student.academic,
        student.schoolId, student.collegeId, student.depId, student.specialtyId, student.classesId,
        student.sort, student.remark, student.modifyTime, student.modifyUser, student.id
      )
    ) {
      messageException(message, it)
      log.debug("Success compete update student")
      message.reply("Success")
    }
  }

  @Throws(Exception::class)
  private suspend fun studentClass(body: JsonObject, connection: SqlConnection): JsonObject {
    val classId = connection.preparedQueryAwait(STUDENT_CLASS_ID, Tuple.of(body.getLong("userId")))
      .first().getLong("classes_id")
    val info = connection.preparedQueryAwait(EveryRepositoryImpl.DATA_NAME, Tuple.of(classId))
      .joinToString(" / ") { row -> row.getString("name") }
    val students = connection.preparedQueryAwait(
      STUDENT_USER_CLASS, Tuple.of(
        classId,
        "%${body.getString("name")}%", "%${body.getString("no")}%"
      )
    )
      .map { row ->
        jsonObjectOf(
          "name" to row.getString("name"),
          "no" to row.getString("no"),
          "gender" to row.getString("gender"),
          "image" to row.getString("image")
        )
      }
    return jsonObjectOf(
      "info" to info,
      "students" to students
    )
  }

  @Throws(Exception::class)
  private suspend fun studentDep(body: JsonObject, connection: SqlConnection): JsonObject {
    val depId = connection.preparedQueryAwait(STUDENT_CLASS_ID, Tuple.of(body.getLong("userId")))
      .first().getLong("dep_id")
    val info = connection.preparedQueryAwait(EveryRepositoryImpl.DATA_NAME, Tuple.of(depId))
      .joinToString(" / ") { row -> row.getString("name") }
    val classId = body.getLong("classId")
    val result = jsonObjectOf("info" to info)
    val classes = connection.preparedQueryAwait(STUDENT_CLASSES_DEP, Tuple.of(depId))
      .map { row -> jsonObjectOf("id" to row.getLong("id"), "name" to row.getString("name")) }
    if (classId === null || !classes.map { it.getLong("id") }.contains(classId)) {
      return result.put("classes", classes)
    }
    val view = body.getJsonArray("resource")
      .map { res -> res as JsonObject }
      .find { res ->
        res.getString("url") == "/student/*" && res.getString("method") == "GET"
      }
    val rowSet = connection.preparedQueryAwait(
      STUDENT_USER_CLASS, Tuple.of(
        classId, "%${body.getString("name")}%", "%${body.getString("no")}%"
      )
    )
    // If this user doesn't have modify permission.
    if (Objects.isNull(view)) {
      val students = rowSet.map { row ->
        jsonObjectOf(
          "gender" to row.getString("gender"),
          "no" to row.getString("no"),
          "image" to row.getString("image"),
          "name" to row.getString("name"),
          "view" to false
        )
      }
      return result.put("students", students)
    }
    // Edit permission.
    val edit = body.getJsonArray("resource")
      .map { res -> res as JsonObject }
      .find { res ->
        res.getString("url") == "/student" && res.getString("method") == "PATCH"
      }
    // If this user has this permission, give he all information.
    val students = rowSet.map { row ->
      row.toJsonObject<Student>()
        .put("image", row.getString("image"))
        .put("view", true)
        .put("edit", Objects.nonNull(edit))
    }
    return result.put("students", students)
  }

}
