package cn.edu.gzmu.center.me

import cn.edu.gzmu.center.model.extension.toTypeArray
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午5:48
 */
interface MeRepository {

  /**
   * Get current user routes by roles.
   * Roles come from [message] body.
   */
  suspend fun roleRoutes(message: Message<JsonArray>)

}

class MeRepositoryImpl(private val connection: SqlConnection) : MeRepository {

  companion object {
    val ROLE_ROUTES = """
      SELECT acr.name
      FROM auth_center_res acr
               LEFT JOIN
           auth_center_role_res acrr on acr.id = acrr.res_id
               LEFT JOIN sys_role sr on sr.id = acrr.role_id
      WHERE acr.type = 0
        AND ( sr.name = any ($1) OR sr.name = 'ROLE_PUBLIC')
    """.trimIndent()
  }

  override suspend fun roleRoutes(message: Message<JsonArray>) {
    connection.preparedQuery(ROLE_ROUTES, Tuple.of(message.body().toTypeArray<String>())) {
      if (it.failed()) {
        message.fail(500, it.cause().message)
        throw it.cause()
      }
      val result = jsonObjectOf()
      it.result().map { res -> res.getString("name")}.forEach { res ->  result.put(res, true) }
      message.reply(result)
    }
  }

}
