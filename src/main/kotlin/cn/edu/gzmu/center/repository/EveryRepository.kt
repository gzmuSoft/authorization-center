package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.extension.toTypeArray
import io.vertx.core.Future
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Basic Repository.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/8 下午1:22
 */
interface EveryRepository {

  /**
   * Get sys data by type.
   * Type come from [message].
   */
  fun dataType(message: Message<Long>)

  /**
   * Get sys data by type.
   * Type come from [message].
   */
  fun dataTypes(message: Message<JsonArray>)

  /**
   * Get sys data by id.
   * Id come from [message].
   */
  fun dataInfo(message: Message<Long>)

  /**
   * User exist, these params must have one/
   * name - optional - String
   * email - optional - String
   * phone - optional - String
   */
  fun userExist(message: Message<JsonObject>)

}

class EveryRepositoryImpl(private val pool: PgPool) : BaseRepository(pool),
  EveryRepository {
  private val log: Logger = LoggerFactory.getLogger(EveryRepositoryImpl::class.java.name)

  companion object {
    private const val DATA_TYPE =
      "SELECT d.id, d.name, d.brief FROM sys_data d WHERE d.type = $1 AND is_enable = true ORDER BY sort"
    private const val DATA_TYPES =
      "SELECT d.id, d.name, d.brief, d.type, d.sort, d.parent_id FROM sys_data d WHERE d.type = any ($1) AND is_enable = true"
    private const val USER_COUNT = "SELECT count(id) FROM sys_user WHERE is_enable = true"
    val DATA_NAME = """
      WITH RECURSIVE cte as (
          SELECT id, name, parent_id, type
          FROM sys_data
          WHERE id = $1 AND is_enable = true
          UNION ALL
          SELECT d.id, d.name, d.parent_id, d.type
          FROM sys_data d
          JOIN cte c ON c.parent_id = d.id
          where d.is_enable = true
      )
      SELECT id, name, parent_id, type
      FROM cte
      ORDER BY type
    """.trimIndent()
  }

  override fun dataType(message: Message<Long>) {
    pool.preparedQuery(DATA_TYPE, Tuple.of(message.body())) {
      messageException(message, it)
      val result = it.result().map { row ->
        jsonObjectOf(
          "id" to row.getLong("id"),
          "name" to row.getString("name"),
          "brief" to row.getString("brief")
        )
      }
      log.debug("Success get sys data by type: {}", result)
      message.reply(JsonArray(result))
    }
  }

  override fun dataTypes(message: Message<JsonArray>) {
    pool.preparedQuery(DATA_TYPES, Tuple.of(message.body().toTypeArray<Long>())) {
      messageException(message, it)
      val result = jsonObjectOf()
      it.result().map { row ->
          jsonObjectOf(
            "id" to row.getLong("id"),
            "name" to row.getString("name"),
            "brief" to row.getString("brief"),
            "type" to row.getLong("type"),
            "parentId" to row.getLong("parent_id"),
            "sort" to row.getLong("sort")
          )
        }.groupBy { row -> row.getLong("type") }
        .forEach { (key, value) ->
          result.put(key.toString(), value.sortedBy { ele -> ele.getLong("sort") })
        }
      log.debug("Success get sys data by types: {}", message.body())
      message.reply(result)
    }
  }

  override fun dataInfo(message: Message<Long>) {
    val id = message.body()
    pool.preparedQuery(DATA_NAME, Tuple.of(id)) {
      messageException(message, it)
      val result = it.result().map { row ->
        jsonObjectOf(
          "id" to row.getLong("id"),
          "name" to row.getString("name"),
          "type" to row.getString("type"),
          "parentId" to row.getLong("parent_id")
        )
      }
      log.debug("Success get sys data: {}", result)
      message.reply(jsonObjectOf(
        "result" to result,
        "name" to result.joinToString(" / ") { row -> row.getString("name") }
      ))
    }
  }

  override fun userExist(message: Message<JsonObject>) {
    val body = message.body()
    val name = body.getString("name") ?: ""
    val email = body.getString("email") ?: ""
    val phone = body.getString("phone") ?: ""
    val future: Future<RowSet<Row>> =
      if (!name.isBlank()) pool.preparedQuery("$USER_COUNT AND name = $1", Tuple.of(name))
      else if (!email.isBlank()) pool.preparedQuery("$USER_COUNT AND email = $1", Tuple.of(email))
      else pool.preparedQuery("$USER_COUNT AND phone = $1", Tuple.of(phone))
    future
      .onSuccess {
        val count = it.first().getLong("count")
        log.debug("Success get match count: {}", count)
        message.reply(jsonObjectOf("exist" to (count > 0)))
      }
      .onFailure { messageException(message, it) }
  }
}
