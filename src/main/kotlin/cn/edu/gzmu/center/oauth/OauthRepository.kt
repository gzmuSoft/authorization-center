package cn.edu.gzmu.center.oauth

import cn.edu.gzmu.center.model.extension.Address.Companion.RESULT
import cn.edu.gzmu.center.model.extension.toTypeArray
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午1:52
 */
interface OauthRepository {
  /**
   * Get current user can access resource.
   * Roles come from [message] body.
   */
  suspend fun roleResource(message: Message<JsonArray>)
}

class OauthRepositoryImpl(private val connection: SqlConnection) : OauthRepository {
  private val log: Logger = LoggerFactory.getLogger(OauthRepositoryImpl::class.java.name)
  companion object {
    val ROLE_RESOURCE = """
      SELECT acr.url, acr.method, sr.name
      FROM auth_center_res acr
               LEFT JOIN
           auth_center_role_res acrr on acr.id = acrr.res_id
               LEFT JOIN sys_role sr on sr.id = acrr.role_id
      WHERE acr.type = 1
        AND ( sr.name = any ($1) OR sr.name = 'ROLE_PUBLIC')
        ORDER BY acr.sort
    """.trimIndent()
  }

  override suspend fun roleResource(message: Message<JsonArray>) {
    connection.preparedQuery(ROLE_RESOURCE, Tuple.of(message.body().toTypeArray<String>())) {
      if (it.failed()) {
        message.fail(500, it.cause().message)
        throw it.cause()
      }
      val result = it.result().map { res ->
        jsonObjectOf(
          "url" to res.getString("url"),
          "method" to res.getString("method"),
          "role" to res.getString("name")
        )
      }
      log.debug("Success get role resource: {}", result)
      message.reply(jsonObjectOf(RESULT to result))
    }
  }

}
