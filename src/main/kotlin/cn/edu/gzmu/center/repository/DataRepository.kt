package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.SysData
import cn.edu.gzmu.center.model.extension.addOptional
import cn.edu.gzmu.center.model.extension.addOrNull
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.model.extension.toJsonObject
import cn.edu.gzmu.center.model.fileds
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
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

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 下午9:28
 */
interface DataRepository {
  /**
   * Data by type
   */
  fun dataType(message: Message<Long>)

  /**
   * Data by parent
   */
  fun dataParent(message: Message<Long>)

  /**
   * Data delete by id
   */
  fun dataDelete(message: Message<Long>)

  /**
   * Data update
   */
  fun dataUpdate(message: Message<JsonObject>)

  /**
   * Data create
   */
  fun dataCreate(message: Message<JsonObject>)

  /**
   * Data page
   */
  suspend fun dataPage(message: Message<JsonObject>)
}

class DataRepositoryImpl(private val pool: PgPool) : BaseRepository(), DataRepository {
  private val log: Logger = LoggerFactory.getLogger(DataRepositoryImpl::class.java.name)
  private val table = "sys_data"
  override fun dataType(message: Message<Long>) {
    dataByOneField(message, "type")
  }

  override fun dataParent(message: Message<Long>) {
    dataByOneField(message, "parent_id")
  }

  override fun dataDelete(message: Message<Long>) {
    val id = message.body()
    val sql = "UPDATE $table SET is_enable = $1 WHERE is_enable = true and id = $2"
    pool.preparedQuery(sql, Tuple.of(false, id)) {
      messageException(message, it)
      log.debug("Success delete data: {}", id)
      message.reply("success")
    }
  }

  override fun dataUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val data = body.mapAs(SysData.serializer())
    val sql = Sql(table)
      .update().set { "modify_user" }.setIf(data::modifyTime)
      .setIf(data::name).setIf(data::brief).setIf(data::remark)
      .setIf(data::isEnable)
      .where("id")
      .get()
    pool.preparedQuery(
      sql, Tuple.of(data.modifyUser, data.modifyTime)
        .addOptional(data.name).addOptional(data.brief).addOptional(data.remark)
        .addOptional(data.isEnable).addOptional(data.id)
    ) {
      messageException(message, it)
      log.debug("Success update data: {}", data)
      message.reply("success")
    }
  }

  override fun dataCreate(message: Message<JsonObject>) {
    val body = message.body()
    val data = body.mapAs(SysData.serializer())
    val sql = Sql(table)
      .insert(
        data::createUser, data::createTime, data::name, data::brief,
        data::parentId, data::type, data::sort, data::remark
      )
      .get()
    pool.preparedQuery(
      sql, Tuple.of(data.createUser, data.createTime).addOrNull(data.name)
        .addOrNull(data.brief).addOrNull(data.parentId).addOrNull(data.type)
        .addOrNull(data.sort).addOrNull(data.remark)
    ) {
      messageException(message, it)
      log.debug("Success create data: {}", data)
      message.reply("success")
    }
  }

  override suspend fun dataPage(message: Message<JsonObject>) {
    val body = message.body()
    val type = body.getLong("type")
    val name = body.getString("name")
    val baseSql = Sql(table)
      .select(SysData::class)
      .whereEnable()
      .and { "type" to type }
      .like { "name" }
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val count = connection.preparedQueryAwait(baseSql.count(), Tuple.of(type, "%$name"))
        .first().getLong("count")
      val sql = baseSql.page(body.getString("sort"))
        .get()
      val rowSet =
        connection.preparedQueryAwait(sql, Tuple.of(type, "%$name%", body.getLong("size"), body.getLong("offset")))
      val content = rowSet.map { it.toJsonObject<SysData>() }
      log.debug("Success get page data: {}", count)
      log.debug("Success get page data: {}", content)
      message.reply(jsonObjectOf("content" to content, "itemsLength" to count))
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    } finally {
      connection?.close()
      log.debug("Close temporary database connection.")
    }
  }

  private fun dataByOneField(message: Message<Long>, field: String) {
    val id = message.body()
    val sql = "SELECT ${fileds(SysData::class)} FROM $table WHERE $field = \$1"
    pool.preparedQuery(sql, Tuple.of(id)) {
      messageException(message, it)
      val data = it.result().map { row -> row.toJsonObject<SysData>() }
      log.debug("Success get data by $field: {}", data)
      message.reply(JsonArray(data))
    }
  }

}
