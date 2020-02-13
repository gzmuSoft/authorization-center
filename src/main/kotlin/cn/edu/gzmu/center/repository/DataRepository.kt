package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.SysData
import cn.edu.gzmu.center.model.extension.addOptional
import cn.edu.gzmu.center.model.extension.addOrNull
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.model.extension.toJsonObject
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 下午9:28
 */
interface DataRepository {
  /**
   * College by type
   */
  fun collegeType(message: Message<Long>)

  /**
   * College by parent
   */
  fun collegeParent(message: Message<Long>)

  /**
   * College update
   */
  fun collegeUpdate(message: Message<JsonObject>)

  /**
   * College create
   */
  fun collegeCreate(message: Message<JsonObject>)
}

class DataRepositoryImpl(private val pool: PgPool) : BaseRepository(), DataRepository {
  private val log: Logger = LoggerFactory.getLogger(DataRepositoryImpl::class.java.name)
  private val table = "sys_data"
  override fun collegeType(message: Message<Long>) {
    collegeByOneField(message, "type")
  }

  override fun collegeParent(message: Message<Long>) {
    collegeByOneField(message, "parent_id")
  }

  override fun collegeUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val college = body.mapAs(SysData.serializer())
    val sql = Sql(table)
      .update().set { "modify_user" }.setIf(college::modifyTime)
      .setIf(college::name).setIf(college::brief).setIf(college::remark)
      .setIf(college::isEnable)
      .where("id")
      .get()
    pool.preparedQuery(
      sql, Tuple.of(college.modifyUser, college.modifyTime)
        .addOptional(college.name).addOptional(college.brief).addOptional(college.remark)
        .addOptional(college.isEnable).addOptional(college.id)
    ) {
      messageException(message, it)
      log.debug("Success update college: {}", college)
      message.reply("success")
    }
  }

  override fun collegeCreate(message: Message<JsonObject>) {
    val body = message.body()
    val college = body.mapAs(SysData.serializer())
    val sql = Sql(table)
      .insert(
        college::createUser, college::createTime, college::name, college::brief,
        college::parentId, college::type, college::sort, college::remark
      )
      .get()
    pool.preparedQuery(
      sql, Tuple.of(college.createUser, college.createTime).addOrNull(college.name)
        .addOrNull(college.brief).addOrNull(college.parentId).addOrNull(college.type)
        .addOrNull(college.sort).addOrNull(college.remark)
    ) {
      messageException(message, it)
      log.debug("Success create college: {}", college)
      message.reply("success")
    }
  }

  private fun collegeByOneField(message: Message<Long>, field: String) {
    val id = message.body()
    val sql = Sql(table)
      .select(SysData::class)
      .where(field)
      .get()
    pool.preparedQuery(sql, Tuple.of(id)) {
      messageException(message, it)
      val college = it.result().map { row -> row.toJsonObject<SysData>() }
      log.debug("Success get college by $field: {}", college)
      message.reply(JsonArray(college))
    }
  }

}
