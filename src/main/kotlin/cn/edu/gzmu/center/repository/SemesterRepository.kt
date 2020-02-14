package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.Semester
import cn.edu.gzmu.center.model.extension.addOptional
import cn.edu.gzmu.center.model.extension.addOrNull
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

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/14 下午12:12
 */
interface SemesterRepository {
  /**
   * semester by school page.
   */
  suspend fun semesterSchool(message: Message<JsonObject>)

  /**
   * semester update.
   */
  fun semesterUpdate(message: Message<JsonObject>)

  /**
   * semester create.
   */
  fun semesterCreate(message: Message<JsonObject>)
}

class SemesterRepositoryImpl(private val pool: PgPool) : BaseRepository(), SemesterRepository {
  private val log: Logger = LoggerFactory.getLogger(SemesterRepositoryImpl::class.java.name)
  private val table = "semester"
  override suspend fun semesterSchool(message: Message<JsonObject>) {
    val body = message.body()
    val schoolId = body.getLong("schoolId")
    val name = body.getString("name")
    val baseSql = Sql(table)
      .select(Semester::class)
      .where("schoolId")
      .like { "name" }
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val count = connection.preparedQueryAwait(baseSql.count(), Tuple.of(schoolId, "%$name%"))
        .first().getLong("count")
      val sql = baseSql.page(body.getString("sort")).get()
      val rowSet = connection.preparedQueryAwait(
        sql, Tuple.of(
          schoolId, "%$name%",
          body.getLong("size"), body.getLong("offset")
        )
      )
      val content = rowSet.map { it.toJsonObject<Semester>() }
      log.debug("Success get page semester: {}", count)
      message.reply(jsonObjectOf("content" to content, "itemsLength" to count))
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    } finally {
      connection?.close()
      log.debug("Close temporary database connection.")
    }
  }

  override fun semesterUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val semester = body.mapAs(Semester.serializer())
    val sql = Sql(table)
      .update()
      .set { "modify_user" }.setIf(semester::modifyTime)
      .setIf(semester::name).setIf(semester::startDate).setIf(semester::endDate)
      .setIf(semester::sort).setIf(semester::remark)
      .where("id")
      .get()
    pool.preparedQuery(
      sql, Tuple.of(semester.modifyUser, semester.modifyTime)
        .addOptional(semester.name).addOptional(semester.startDate).addOptional(semester.endDate)
        .addOptional(semester.sort).addOptional(semester.remark).addLong(semester.id)
    ) {
      messageException(message, it)
      log.debug("Success update data: {}", semester)
      message.reply("success")
    }
  }

  override fun semesterCreate(message: Message<JsonObject>) {
    val body = message.body()
    val semester = body.mapAs(Semester.serializer())
    val sql = Sql(table)
      .insert(
        semester::createUser, semester::createTime, semester::name,
        semester::schoolId, semester::startDate, semester::endDate,
        semester::sort, semester::remark
      ).get()
    pool.preparedQuery(
      sql, Tuple.of(semester.createUser, semester.createTime)
        .addOrNull(semester.name).addOrNull(semester.schoolId).addOrNull(semester.startDate)
        .addOrNull(semester.endDate).addOrNull(semester.sort).addOrNull(semester.remark)
    ) {
      messageException(message, it)
      log.debug("Success create data: {}", semester)
      message.reply("success")
    }
  }

}
