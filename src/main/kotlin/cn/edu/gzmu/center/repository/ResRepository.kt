package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.entity.AuthCenterRes
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
 * @date 2020/2/12 上午12:42
 */
interface ResRepository {

  /**
   * Get res page.
   */
  suspend fun res(message: Message<JsonObject>)

  /**
   * res update.
   */
  fun resUpdate(message: Message<JsonObject>)

  /**
   * res update.
   */
  fun resCreate(message: Message<JsonObject>)

  /**
   * res delete.
   */
  fun resDelete(message: Message<Long>)

  /**
   * res delete.
   */
  fun resEnable(message: Message<Long>)
}

class ResRepositoryIImpl(private val pool: PgPool) : BaseRepository(pool), ResRepository {
  private val log: Logger = LoggerFactory.getLogger(ResRepositoryIImpl::class.java.name)
  private val table = "auth_center_res"
  companion object {
    private const val RES_ENABLE = """
      UPDATE auth_center_res SET is_enable = $1 WHERE id = $2
    """
  }
  override suspend fun res(message: Message<JsonObject>) {
    val body = message.body()
    val type = body.getLong("type")
    val sqlGet = Sql("auth_center_res")
      .select(AuthCenterRes::class)
      .whereLike("describe")
    when (type) {
      // Get menu —— name is route, url is menu name, method is menu icon, remark is mark
      1L -> sqlGet.and("remark IS NOT NULL")
        .and("name IS NOT NULL")
        .and("url IS NOT NULL")
        .and("method IS NOT NULL")
      // Get resource —— name and remark is null
      2L -> sqlGet.and("remark IS NULL")
        .and("name IS NUll")
      // Get route —— name is route, url is null, others is default.
      3L -> sqlGet.and("remark IS NULL")
        .and("url IS NULL")
        .and("name IS NOT NULL")
      // Get resource action —— name、remark、method are not null.
      4L -> sqlGet.and("remark IS NOT NULL")
        .and("name IS NOT NULL")
        .and("method IS NOT NULL")
        .and("url IS NULL")
    }
    var connection: SqlConnection? = null
    try {
      connection = pool.getConnectionAwait()
      val count =
        connection.preparedQueryAwait(sqlGet.count(), Tuple.of("%${body.getString("describe")}%"))
          .first().getLong("count")
      val sql = sqlGet
        .page(body.getString("sort"))
        .get()
      val rowSet = connection.preparedQueryAwait(
        sql, Tuple.of(
          "%${body.getString("describe")}%",
          body.getLong("size"), body.getLong("offset")
        )
      )
      val content = rowSet.map { it.toJsonObject<AuthCenterRes>() }
      log.debug("Success get page res: {}", count)
      message.reply(jsonObjectOf("content" to content, "itemsLength" to count))
    } catch (e: Exception) {
      message.fail(500, e.cause?.message)
      throw e
    } finally {
      connection?.close()
      log.debug("Close temporary database connection.")
    }
  }

  override fun resUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val res = body.mapAs(AuthCenterRes.serializer())
    val sql = Sql(table)
      .update().set { "id" }.setIf(res::modifyTime).setIf(res::modifyUser)
      .setIf(res::name).setIf(res::url).setIf(res::describe).setIf(res::method)
      .setIf(res::sort).setIf(res::remark).whereEnable()
      .and { "id" to res.id }.get()
    val params = Tuple.of(res.id, res.modifyTime, res.modifyUser).addOptional(res.name)
      .addOptional(res.url).addOptional(res.describe).addOptional(res.method)
      .addOptional(res.sort).addOptional(res.remark).addOptional(res.id)
    pool.preparedQuery(sql, params) {
      messageException(message, it)
      log.debug("Success update res: {}", res)
      message.reply("success")
    }
  }

  override fun resCreate(message: Message<JsonObject>) {
    val body = message.body()
    val sql = Sql(table)
      .insert("create_user", "create_time", "name", "url", "describe", "method", "sort", "remark")
      .get()
    val res = body.mapAs(AuthCenterRes.serializer())
    pool.preparedQuery(
      sql, Tuple.of(res.createUser, res.createTime).addOrNull(res.name).addOrNull(res.url)
        .addOrNull(res.describe).addOrNull(res.method).addOrNull(res.sort).addOrNull(res.remark)
    ) {
      messageException(message, it)
      log.debug("Success create res: {}", res)
      message.reply("success")
    }
  }

  override fun resDelete(message: Message<Long>) {
    resIsEnable(message, false)
  }

  override fun resEnable(message: Message<Long>) {
    resIsEnable(message, true)
  }

  private fun resIsEnable(message: Message<Long>, enable: Boolean) {
    val id = message.body()
    pool.preparedQuery(RES_ENABLE, Tuple.of(enable, id)) {
      messageException(message, it)
      log.debug("Success update res enable: {}", id)
      message.reply("success")
    }
  }
}
