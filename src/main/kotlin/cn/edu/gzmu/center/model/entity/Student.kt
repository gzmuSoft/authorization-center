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
 * @date 2020/2/7 下午11:46
 */
@Serializable
data class Student (
  val id: Long? = null,
  val name: String? = null,
  val spell: String? = null,
  val userId: Long? = null,
  val schoolId: Long? = null,
  val collegeId: Long? = null,
  val depId: Long? = null,
  val specialtyId: Long? = null,
  val classesId: Long? = null,
  val no: String? = null,
  val gender: String? = null,
  val idNumber: String? = null,
  @Serializable(with = LocalDateSerializer::class)
  val birthday: LocalDate? = null,
  @Serializable(with = LocalDateSerializer::class)
  val enterDate: LocalDate? = null,
  val academic: String? = null,
  @Serializable(with = LocalDateSerializer::class)
  val graduationDate: LocalDate? = null,
  val graduateInstitution: String? = null,
  val originalMajor: String? = null,
  val resume: String? = null,
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
