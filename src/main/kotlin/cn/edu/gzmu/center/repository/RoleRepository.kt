package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.and
import cn.edu.gzmu.center.model.entity.SysRole
import cn.edu.gzmu.center.model.extension.addOptional
import cn.edu.gzmu.center.model.extension.mapAs
import cn.edu.gzmu.center.model.extension.toJsonObject
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/11 下午12:29
 */
interface RoleRepository {
  /**
   * Get role by parent id.
   * The id is coming from [message] body.
   */
  fun roleParent(message: Message<Long>)

  /**
   * Get role res by id.
   * The id is coming from [message] body.
   */
  fun roleRes(message: Message<Long>)

  /**
   * Update role.
   */
  fun roleUpdate(message: Message<JsonObject>)

  /**
   * Add role.
   */
  fun roleAdd(message: Message<JsonObject>)
}

class RoleRepositoryImpl(private val pool: PgPool) : BaseRepository(pool), RoleRepository {
  companion object {
    private const val ROLE_ADD = """
      INSERT INTO sys_role (name, des, icon_cls, parent_id, create_user, create_time,
      modify_user, modify_time, is_enable, remark) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
    """
  }

  private val log: Logger = LoggerFactory.getLogger(RoleRepositoryImpl::class.java.name)
  override fun roleParent(message: Message<Long>) {
    val parentId = message.body()
    val sql = Sql("sys_role")
      .select(SysRole::class)
      .where("parent_id")
      .orderBy { "sort" }
      .get()
    pool.preparedQuery(sql, Tuple.of(parentId)) {
      messageException(message, it)
      val result = it.result().map { row ->
        row.toJsonObject<SysRole>()
      }
      log.debug("Success get role by parent: {}", result)
      message.reply(JsonArray(result))
    }
  }

  override fun roleRes(message: Message<Long>) {
    val id = message.body()
    val sql = Sql("auth_center_res acr")
      .select { "acr.id" and "acr.name" and "acr.describe" and "acr.url" and "acr.method" and "acr.remark" }
      .leftJoin { "auth_center_role_res acrr" }
      .on { "acr.id = acrr.res_id" }
      .leftJoin { "sys_role sr" }
      .on { "sr.id = acrr.role_id" }
      .where { "acr.is_enable = true" }
      .and("sr.id = $1")
      .orderBy { "acr.sort" }.get()
    pool.preparedQuery(sql, Tuple.of(id)) {
      messageException(message, it)
      val result = it.result().map { row ->
        jsonObjectOf(
          "id" to row.getLong("id"),
          "describe" to row.getString("describe"),
          "name" to row.getString("name"),
          "url" to row.getString("url"),
          "method" to row.getString("method"),
          "remark" to row.getString("remark")
        )
      }
      message.reply(JsonArray(result))
    }
  }

  override fun roleUpdate(message: Message<JsonObject>) {
    val body = message.body()
    val role = body.mapAs(SysRole.serializer())
    val sql = Sql("sys_role")
      .update().set { "id" }.setIf(role::name)
      .setIf(role::modifyTime).setIf(role::modifyUser).setIf(role::des)
      .setIf(role::iconCls).setIf(role::sort).setIf(role::remark).setIf(role::isEnable)
      .where("id")
      .get()
    val tuple = Tuple.of(role.id).addOptional(role.name)
      .addOptional(role.modifyTime).addOptional(role.modifyUser).addOptional(role.des)
      .addOptional(role.iconCls).addOptional(role.sort).addOptional(role.remark)
      .addOptional(role.isEnable).addOptional(role.id)
    pool.preparedQuery(sql, tuple) {
      messageException(message, it)
      log.debug("Success update role: {}", role)
      message.reply("success")
    }
  }

  override fun roleAdd(message: Message<JsonObject>) {
    val role = message.body().mapAs(SysRole.serializer())
    pool.preparedQuery(
      ROLE_ADD, Tuple.of(
        role.name, role.des, role.iconCls, role.parentId,
        role.createUser, role.createTime, role.modifyUser,
        role.modifyTime, role.isEnable, role.remark
      )
    ) {
      messageException(message, it)
      log.debug("Success update role: {}", role)
      message.reply("success")
    }
  }
}
