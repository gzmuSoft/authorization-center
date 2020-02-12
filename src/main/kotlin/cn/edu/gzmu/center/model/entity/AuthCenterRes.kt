package cn.edu.gzmu.center.model.entity

import cn.edu.gzmu.center.model.extension.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * Resource.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午12:58
 */
@Serializable
data class AuthCenterRes(
  val id: Long? = null,
  // If name not null, the resource is menu.
  val name: String? = null,
  val url: String? = null,
  val describe: String? = null,
  // Only GET POST PATCH PUT DELETE
  val method: String = "GET",
  val spell: String? = null,
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
