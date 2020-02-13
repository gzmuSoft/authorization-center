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
)
