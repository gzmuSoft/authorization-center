package cn.edu.gzmu.center.base

import cn.edu.gzmu.center.model.extension.messageException
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Basic Repository.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/8 下午1:22
 */
interface BaseRepository {

  /**
   * Get sys data by type.
   * Type come from [message].
   */
  fun dataType(message: Message<Long>)

  /**
   * Get sys data by id.
   * Id come from [message].
   */
  fun dataInfo(message: Message<Long>)

}

class BaseRepositoryImpl(private val connection: SqlConnection) : BaseRepository {
  private val log: Logger = LoggerFactory.getLogger(BaseRepositoryImpl::class.java.name)

  companion object {
    const val DATA_TYPE =
      "SELECT d.id, d.name, d.brief FROM sys_data d WHERE d.type = $1 AND is_enable = true ORDER BY sort"
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
    connection.preparedQuery(DATA_TYPE, Tuple.of(message.body())) {
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

  override fun dataInfo(message: Message<Long>) {
    val id = message.body()
    connection.preparedQuery(DATA_NAME, Tuple.of(id)) {
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
}
