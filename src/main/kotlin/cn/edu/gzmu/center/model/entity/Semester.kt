package cn.edu.gzmu.center.model.entity

import cn.edu.gzmu.center.model.extension.LocalDateSerializer
import cn.edu.gzmu.center.model.extension.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/14 下午12:45
 */
@Serializable
data class Semester(
  val id: Long? = null,
  val name: String? = null,
  val spell: String? = null,
  val schoolId: Long? = null,
  @Serializable(with = LocalDateSerializer::class)
  val startDate: LocalDate? = null,
  @Serializable(with = LocalDateSerializer::class)
  val endDate: LocalDate? = null,
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
  companion object{
    const val ADDRESS_SEMESTER_SCHOOL = "address_semester_school"
    const val ADDRESS_SEMESTER_UPDATE = "address_semester_update"
    const val ADDRESS_SEMESTER_CREATE = "address_semester_create"
  }
}
