package cn.edu.gzmu.center.repository

import cn.edu.gzmu.center.base.BaseRepository
import io.vertx.core.eventbus.Message
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.pgclient.preparedQueryAwait
import io.vertx.pgclient.PgPool
import java.lang.Exception

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/5/19 下午9:39
 */
interface DashboardRepository {

  suspend fun dashboard(message: Message<Unit>)

}

class DashboardRepositoryImpl(private val pool: PgPool) : BaseRepository(pool), DashboardRepository {

  companion object {
    const val USER_COUNT = "SELECT count('id') FROM sys_user WHERE is_enable = true"
    const val STUDENT_COUNT = "SELECT count('id') FROM student WHERE is_enable = true"
    const val TEACHER_COUNT = "SELECT count('id') FROM teacher WHERE is_enable = true"
    const val CLIENT_COUNT = "SELECT count('id') FROM client_details WHERE is_enable = true"
    const val ROLE_COUNT = "SELECT count('id') FROM sys_role WHERE is_enable = true"
  }

  override suspend fun dashboard(message: Message<Unit>) {
    try {
      val userCount = pool.preparedQueryAwait(USER_COUNT).first().getLong("count")
      val studentCount = pool.preparedQueryAwait(STUDENT_COUNT).first().getLong("count")
      val teacherCount = pool.preparedQueryAwait(TEACHER_COUNT).first().getLong("count")
      val clientCount = pool.preparedQueryAwait(CLIENT_COUNT).first().getLong("count")
      val roleClient = pool.preparedQueryAwait(ROLE_COUNT).first().getLong("count")
      message.reply(jsonObjectOf(
        "userCount" to userCount,
        "studentCount" to studentCount,
        "teacherCount" to teacherCount,
        "clientCount" to clientCount,
        "roleCount" to roleClient
      ))
    } catch (e: Exception) {
      messageException(message, e)
    }
  }

}

