package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.entity.Client
import cn.edu.gzmu.center.model.extension.encodePassword
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.model.extension.toJsonObject
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/23 下午8:20
 */
interface ClientRepository {
  /**
   * Get all client.
   */
  fun client(message: Message<Unit>)

  /**
   * Insert or update client.
   */
  fun clientUpdate(message: Message<JsonObject>)
}

class ClientRepositoryImpl(private val pool: PgPool) : BaseRepository(pool), ClientRepository {
  private val log: Logger = LoggerFactory.getLogger(ClientRepositoryImpl::class.java.name)

  companion object {
    private const val TABLE = "client_details"
    private const val CLIENT_ALL = "SELECT * FROM $TABLE ORDER BY sort"
    private val CLIENT_INSERT = """
      INSERT INTO client_details(name, client_id, resource_ids, client_secret, scope,
      grant_types, access_token_validity, refresh_token_validity, redirect_url, sort,
      remark, create_time, create_user, is_enable, spell)
      VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15)
    """.trimIndent()
    private val CLIENT_UPDATE = """
      UPDATE client_details SET name = $1, client_id = $2, resource_ids = $3, client_secret = $4,
      scope = $5, grant_types = $6, access_token_validity = $7, refresh_token_validity = $8,
      redirect_url = $9, sort = $10, remark = $11, modify_time = $12, create_user = $13,
      is_enable = $14, spell = $15 WHERE id = $16
    """.trimIndent()
  }

  override fun client(message: Message<Unit>) {
    pool.preparedQuery(CLIENT_ALL) {
      messageException(message, it)
      val result = it.result().map { it.toJsonObject<Client>() }
      log.debug("Success get client: {}", result.size)
      message.reply(JsonArray(result))
    }
  }

  override fun clientUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val client = body.mapAs(Client.serializer())
    val tuple = Tuple.of(
      client.name, client.clientId, client.resourceIds, encodePassword(client.spell ?: "0"),
      client.scope, client.grantTypes, client.accessTokenValidity, client.refreshTokenValidity,
      client.redirectUrl, client.sort, client.remark, client.modifyTime, client.createUser,
      client.isEnable, client.spell ?: "000000"
    )
    val (sql, params) =
      if (Objects.isNull(client.id)) Pair(CLIENT_INSERT, tuple)
      else Pair(CLIENT_UPDATE, tuple.addLong(client.id))
    pool.preparedQuery(sql, params) {
      messageException(message, it)
      log.debug("Success action client!")
      message.reply("Success")
    }
  }
}
