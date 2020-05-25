package cn.edu.gzmu.center.model.entity

import cn.edu.gzmu.center.model.extension.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * sys role.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/11 下午12:38
 */
@Serializable
data class SysRole(
  val id: Long? = null,
  val name: String? = null,
  val spell: String? = null,
  val iconCls: String? = null,
  val des: String? = null,
  val parentId: Long = 0,
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
    const val ADDRESS_ROLE_PARENT = "address_role_parent"
    const val ADDRESS_ROLE_RES = "address_role_res"
    const val ADDRESS_ROLE_UPDATE = "address_role_update"
    const val ADDRESS_ROLE_ADD = "address_role_add"
  }
}
