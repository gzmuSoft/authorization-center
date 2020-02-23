package cn.edu.gzmu.center.model.entity

import cn.edu.gzmu.center.model.extension.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/23 下午8:08
 */
@Serializable
data class Client(
  val id: Long? = null,
  val name: String? = null,
  val spell: String? = null,
  val clientId: String? = null,
  val resourceIds: String? = null,
  val clientSecret: String? = null,
  val scope: String? = null,
  val grantTypes: String? = null,
  val redirectUrl: String? = null,
  val authorities: String? = null,
  val accessTokenValidity: Long? = null,
  val refreshTokenValidity: Long? = null,
  val additionalInformation: String = "{\"a\":\"1\"}",
  val autoApproveScopes: String = "",
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
    const val ADDRESS_CLIENT = "address_client"
  }
}
