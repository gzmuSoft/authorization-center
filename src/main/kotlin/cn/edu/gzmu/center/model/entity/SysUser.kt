package cn.edu.gzmu.center.model.entity

import cn.edu.gzmu.center.model.extension.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/5 下午5:31
 */
@Serializable
data class SysUser(
  val id: Long? = null,
  val name: String? = null,
  val spell: String? = null,
  val password: String? = null,
  val status: String? = null,
  val image: String? = null,
  val avatar: String? = null,
  val email: String? = null,
  val phone: String? = null,
  val onlineStatus: Boolean = false,
  val sort: Long = 1,
  val createUser: String? = null,
  @Serializable(with = LocalDateTimeSerializer::class)
  val createTime: LocalDateTime = LocalDateTime.now(),
  val modifyUser: String? = null,
  @Serializable(with = LocalDateTimeSerializer::class)
  val modifyTime: LocalDateTime = LocalDateTime.now(),
  val remark: String? = null,
  val isEnable: Boolean = false
) {
  companion object {
    const val ADDRESS_USER_ONE = "address_user_one"
    const val ADDRESS_USER_PASSWORD = "address_user_password"
    const val ADDRESS_USER_UPDATE = "address_user_update"
  }
}

