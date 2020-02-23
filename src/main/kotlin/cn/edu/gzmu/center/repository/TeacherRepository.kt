package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.SysUser
import cn.edu.gzmu.center.model.entity.Teacher
import cn.edu.gzmu.center.model.extension.addOptional
import cn.edu.gzmu.center.model.extension.encodePassword
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
import java.time.LocalDateTime

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/22 下午4:20
 */
interface TeacherRepository {
  /**
   * Teacher page
   */
  suspend fun teacherPage(message: Message<JsonObject>)

  /**
   * Teacher update
   */
  fun teacherUpdate(message: Message<JsonObject>)

  /**
   * Teacher add
   */
  fun teacherAdd(message: Message<JsonObject>)

  /**
   * Teacher import
   * [message]:
   * content ---- student info
   * config  ---- other config
   * createUser ----  create user
   */
  suspend fun teacherImport(message: Message<JsonObject>)
}

class TeacherRepositoryImpl(private val pool: PgPool) : BaseRepository(pool), TeacherRepository {
  private val table = "teacher"
  private val log: Logger = LoggerFactory.getLogger(TeacherRepositoryImpl::class.java.name)

  companion object {
    private val TEACHER_UPDATE = """
      UPDATE teacher SET name = $2, school_id = $3, college_id = $4, dep_id = $5,
      gender = $6, birthday = $7, graduation_date = $8, work_date = $9, nation = $10,
      degree = $11, academic = $12, major = $13, prof_title = $14, prof_title_ass_date = $15,
      graduate_institution = $16, major_research = $17, subject_category = $18, id_number = $19,
      is_academic_leader = $20, sort = $21, remark = $22, is_enable = $23, modify_time = $24,
      modify_user = $25 WHERE id = $1
    """.trimIndent()
    private val TEACHER_INSERT_NOTHING = """
      WITH u as (
        INSERT INTO sys_user(name, password, phone, email, create_user, create_time)
        VALUES ($25, $26, $27, $28, $29, $30)
        ON CONFLICT(name) do update set name = excluded.name
        RETURNING id as userId
      ), role as (
        SELECT id as roleId FROM sys_role WHERE name = 'ROLE_TEACHER'
      ), userRes as (
        INSERT INTO sys_user_role(user_id, role_id)
        VALUES ((SELECT userId FROM u), (SELECT roleId FROM role))
      )
      INSERT INTO teacher(name, school_id, college_id, dep_id, gender, birthday, graduation_date,
      work_date, nation, degree, academic, major, prof_title, prof_title_ass_date, graduate_institution,
      major_research, subject_category, id_number, is_academic_leader, sort, remark, is_enable, create_time,
      create_user, user_id) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16, $17, $18,
      $19, $20, $21, $22, $23, $24, (SELECT userId FROM u)) ON CONFLICT(id_number) DO NOTHING
    """.trimIndent()
    private val TEACHER_INSERT_UPDATE = """
      WITH u as (
        INSERT INTO sys_user(name, password, phone, email, create_user, create_time)
        VALUES ($25, $26, $27, $28, $29, $30)
        ON CONFLICT(name) do update set password = excluded.password, is_enable = true, phone = excluded.phone, email = excluded.email,
        create_user = excluded.create_user, create_time = excluded.create_time
        RETURNING id as userId
      ), role as (
        SELECT id as roleId FROM sys_role WHERE name = 'ROLE_TEACHER'
      ), userRes as (
        INSERT INTO sys_user_role(user_id, role_id)
        VALUES ((SELECT userId FROM u), (SELECT roleId FROM role))
      )
      INSERT INTO teacher(name, school_id, college_id, dep_id, gender, birthday, graduation_date,
      work_date, nation, degree, academic, major, prof_title, prof_title_ass_date, graduate_institution,
      major_research, subject_category, id_number, is_academic_leader, sort, remark, is_enable, create_time,
      create_user, user_id) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16, $17, $18,
      $19, $20, $21, $22, $23, $24, (SELECT userId FROM u)) ON CONFLICT(id_number) DO UPDATE SET name = excluded.name,
      user_id = excluded.user_id, school_id = excluded.school_id, college_id = excluded.college_id, dep_id = excluded.dep_id,
      gender = excluded.gender, birthday = excluded.birthday, graduation_date = excluded.graduation_date,
      work_date = excluded.work_date, nation = excluded.nation, degree = excluded.degree, academic = excluded.academic,
      major = excluded.major, prof_title = excluded.prof_title, prof_title_ass_date = excluded.prof_title_ass_date,
      graduate_institution = excluded.graduate_institution, major_research = excluded.major_research,
      subject_category = excluded.subject_category, id_number = excluded.id_number, sort = excluded.sort,
      is_academic_leader = excluded.is_academic_leader, remark = excluded.remark, is_enable = true,
      modify_time = excluded.create_time, modify_user = excluded.create_user
    """.trimIndent()
  }

  override suspend fun teacherPage(message: Message<JsonObject>) {
    val body = message.body()
    val sort = body.getString("sort")
    body.put("sort", 1)
    val teacher = body.mapAs(Teacher.serializer())
    val baseSql = Sql(table)
      .select(Teacher::class)
      .whereLike(teacher::name)
      .andNonNull(
        teacher::gender, teacher::workDate, teacher::nation, teacher::academic,
        teacher::degree, teacher::profTitle, teacher::schoolId, teacher::collegeId,
        teacher::depId, teacher::isEnable
      )
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val params = Tuple.of("%${teacher.name}%").addOptional(
        teacher.gender, teacher.workDate, teacher.nation, teacher.academic,
        teacher.degree, teacher.profTitle, teacher.schoolId, teacher.collegeId,
        teacher.depId, teacher.isEnable
      )
      val count = connection.preparedQueryAwait(baseSql.count(), params).first().getLong("count")
      val sql = baseSql.page(sort).get()
      val resource = body.getJsonArray("resource").map { it as JsonObject }
      val teacherEditPermission = resource.find { res ->
        res.getString("url") == "/teacher" && res.getString("method") == "PATCH"
      } != null
      val userViewPermission = resource.find { res ->
        res.getString("url") == "/user/*" && res.getString("method") == "GET"
      } != null
      val teacherAddPermission = resource.find { res ->
        res.getString("url") == "/teacher" && res.getString("method") == "POST"
      } != null
      val userEditPermission = resource.find { res ->
        res.getString("url") == "/user" && res.getString("method") == "PATCH"
      } != null
      val teacherImportPermission = resource.find { res ->
        res.getString("url") == "/teacher/import" && res.getString("method") == "POST"
      } != null
      val userPasswordPermission = resource.find { res ->
        res.getString("url") == "/user/password" && res.getString("method") == "PATCH"
      } != null
      val rowSet =
        connection.preparedQueryAwait(sql, params.addLong(body.getLong("size")).addLong(body.getLong("offset")))
      val content = rowSet.map {
        it.toJsonObject<Teacher>()
          .put("edit", teacherEditPermission).put("resetPassword", userPasswordPermission)
          .put("userView", userViewPermission).put("userEdit", userEditPermission)
      }
      log.debug("Success get page data: {}", count)
      message.reply(
        jsonObjectOf(
          "content" to content, "itemsLength" to count,
          "add" to teacherAddPermission, "import" to teacherImportPermission
        )
      )
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    } finally {
      connection?.close()
      log.debug("Close temporary database connection.")
    }
  }

  override fun teacherUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val teacher = body.mapAs(Teacher.serializer())
    val params = Tuple.of(
      teacher.id, teacher.name, teacher.schoolId, teacher.collegeId,
      teacher.depId, teacher.gender, teacher.birthday, teacher.graduationDate,
      teacher.workDate, teacher.nation, teacher.degree, teacher.academic, teacher.major,
      teacher.profTitle, teacher.profTitleAssDate, teacher.graduateInstitution, teacher.majorResearch,
      teacher.subjectCategory, teacher.idNumber, teacher.isAcademicLeader, teacher.sort, teacher.remark,
      teacher.isEnable, teacher.modifyTime, teacher.modifyUser
    )
    pool.preparedQuery(TEACHER_UPDATE, params) {
      messageException(message, it)
      log.debug("Success update teacher: {}", teacher.id)
      message.reply("Success")
    }
  }

  override fun teacherAdd(message: Message<JsonObject>) {
    val body = message.body()
    val teacher = body.mapAs(Teacher.serializer())
    val user = body.mapAs(SysUser.serializer())
    val params = Tuple.of(
      teacher.name, teacher.schoolId, teacher.collegeId, teacher.depId,
      teacher.gender, teacher.birthday, teacher.graduationDate, teacher.workDate,
      teacher.nation, teacher.degree, teacher.academic, teacher.major, teacher.profTitle,
      teacher.profTitleAssDate, teacher.graduateInstitution, teacher.majorResearch,
      teacher.subjectCategory, teacher.idNumber, teacher.isAcademicLeader, teacher.sort,
      teacher.remark, teacher.isEnable, teacher.createTime, teacher.createUser,
      user.phone, encodePassword(teacher.idNumber ?: "0"), user.phone, user.email,
      user.createUser, user.createTime
    )
    pool.preparedQuery(TEACHER_INSERT_NOTHING, params) {
      messageException(message, it)
      log.debug("Success add a teacher: {}", teacher.name)
      message.reply("Success")
    }
  }

  override suspend fun teacherImport(message: Message<JsonObject>) {
    val createTime = LocalDateTime.now()
    import(message, TEACHER_INSERT_NOTHING, TEACHER_INSERT_UPDATE) {content, createUser ->
      content.map {
        it as JsonObject
        val teacher = it.mapAs(Teacher.serializer())
        val phone = it.getString("phone")
        val email = it.getString("email")
        Tuple.of(
          teacher.name, teacher.schoolId, teacher.collegeId, teacher.depId,
          teacher.gender, teacher.birthday, teacher.graduationDate, teacher.workDate,
          teacher.nation, teacher.degree, teacher.academic, teacher.major, teacher.profTitle,
          teacher.profTitleAssDate, teacher.graduateInstitution, teacher.majorResearch,
          teacher.subjectCategory, teacher.idNumber, teacher.isAcademicLeader, teacher.sort,
          teacher.remark, teacher.isEnable, teacher.createTime, createUser,
          phone, encodePassword(teacher.idNumber ?: "0"), phone, email,
          createUser, createTime
        )
      }
    }
  }

}
