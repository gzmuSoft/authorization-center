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
  val id: Long?,
  val name: String?,
  val spell: String?,
  val password: String?,
  val status: String?,
  val image: String?,
  val avatar: String?,
  val email: String?,
  val phone: String?,
  val onlineStatus: Boolean = false,
  val sort: Long = 1,
  val createUser: String?,
  @Serializable(with = LocalDateTimeSerializer::class)
  val createTime: LocalDateTime = LocalDateTime.now(),
  val modifyUser: String?,
  @Serializable(with = LocalDateTimeSerializer::class)
  val modifyTime: LocalDateTime = LocalDateTime.now(),
  val remark: String?,
  val isEnable: Boolean = false
)

