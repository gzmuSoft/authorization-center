package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.Student
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
}

class StudentRepositoryImpl(private val pool: PgPool) : BaseRepository(), StudentRepository {
  private val log: Logger = LoggerFactory.getLogger(StudentRepositoryImpl::class.java.name)
  private val table = "student"

  companion object {
    private const val STUDENT_CLASS_ID =
      "SELECT classes_id, specialty_id FROM student WHERE student.user_id = $1 AND student.is_enable = true"
    private const val STUDENT_CLASSES_SPECIALTY =
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
  }

  override suspend fun studentMe(message: Message<JsonObject>) {
    val body = message.body()
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val result =
        if (body.getBoolean("student")) studentClass(body, connection)
        else studentSpecialty(body, connection)
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
      .setIf(student::birthday).setIf(student::idNumber)
      .where("id").get()
    pool.preparedQuery(
      sql, Tuple.of(
        student.name, student.no, student.gender,
        student.enterDate, student.birthday, student.idNumber, student.id
      )
    ) {
      messageException(message, it)
      log.debug("Success update user info: ", student)
      message.reply("success")
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
  private suspend fun studentSpecialty(body: JsonObject, connection: SqlConnection): JsonObject {
    val specialtyId = connection.preparedQueryAwait(STUDENT_CLASS_ID, Tuple.of(body.getLong("userId")))
      .first().getLong("specialty_id")
    val info = connection.preparedQueryAwait(EveryRepositoryImpl.DATA_NAME, Tuple.of(specialtyId))
      .joinToString(" / ") { row -> row.getString("name") }
    val classId = body.getLong("classId")
    val result = jsonObjectOf("info" to info)
    val classes = connection.preparedQueryAwait(STUDENT_CLASSES_SPECIALTY, Tuple.of(specialtyId))
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
