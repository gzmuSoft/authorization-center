package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.entity.Client
import cn.edu.gzmu.center.model.extension.toJsonObject
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.pgclient.PgPool
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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
}

class ClientRepositoryImpl(private val pool: PgPool) : BaseRepository(pool), ClientRepository {
  private val log: Logger = LoggerFactory.getLogger(ClientRepositoryImpl::class.java.name)

  companion object {
    private const val TABLE = "client_details"
    private const val CLIENT_ALL = "SELECT * FROM $TABLE ORDER BY sort"
  }

  override fun client(message: Message<Unit>) {
    pool.preparedQuery(CLIENT_ALL) {
      messageException(message, it)
      val result = it.result().map { it.toJsonObject<Client>() }
      log.debug("Success get client: {}", result.size)
      message.reply(JsonArray(result))
    }
  }
}
