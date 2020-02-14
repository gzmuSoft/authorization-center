package cn.edu.gzmu.center.model.entity

import cn.edu.gzmu.center.model.extension.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * sys data.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 下午9:34
 */
@Serializable
data class SysData(
  val id: Long? = null,
  val name: String? = null,
  val spell: String? = null,
  val brief: String? = null,
  val parentId: Long = 0,
  val type: Long = 0,
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
    const val ADDRESS_DATA_TYPE = "address_data_type"
    const val ADDRESS_DATA_PARENT = "address_data_parent"
    const val ADDRESS_DATA_UPDATE = "address_data_update"
    const val ADDRESS_DATA_CREATE = "address_data_create"
    const val ADDRESS_DATA_PAGE = "address_data_page"
    const val ADDRESS_DATA_DELETE = "address_data_delete"
  }
}
