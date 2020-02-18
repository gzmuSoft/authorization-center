package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import cn.edu.gzmu.center.model.entity.SysUser
import cn.edu.gzmu.center.model.extension.toJsonObject
import io.vertx.core.eventbus.Message
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/17 下午10:54
 */
interface UserRepository {
  /**
   * Get user by Id
   */
  fun userOne(message: Message<Long>)
}

class UserRepositoryImpl(private val pool: PgPool) : BaseRepository(), UserRepository {
  private val log: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java.name)
  private val table = "sys_user"
  override fun userOne(message: Message<Long>) {
    pool.preparedQuery("SELECT * FROM $table WHERE id = $1", Tuple.of(message.body())) {
      messageException(message, it)
      if (it.result().count() == 0) {
        message.fail(404, "暂无此用户信息")
        return@preparedQuery
      }
      val user = it.result().first().toJsonObject<SysUser>()
      log.debug("Success get user: {}", user)
      user.remove("password")
      message.reply(user)
    }
  }
}
