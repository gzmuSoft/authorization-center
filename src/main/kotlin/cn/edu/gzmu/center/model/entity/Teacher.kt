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
 * @date 2020/2/7 下午11:51
 */
@Serializable
data class Teacher (
  val id: Long? = null,
  val name: String? = null,
  val spell: String? = null,
  val userId: Long? = null,
  val schoolId: Long? = null,
  val collegeId: Long? = null,
  val depId: Long? = null,
  val gender: String? = null,
  @Serializable(with = LocalDateSerializer::class)
  val birthday: LocalDate? = null,
  @Serializable(with = LocalDateSerializer::class)
  val graduationDate: LocalDate? = null,
  @Serializable(with = LocalDateSerializer::class)
  val profTitleAssDate: LocalDate? = null,
  @Serializable(with = LocalDateSerializer::class)
  val workDate: LocalDate? = null,
  val nation: String? = null,
  val degree: String? = null,
  val academic: String? = null,
  val major: String? = null,
  val profTitle: String? = null,
  val graduateInstitution: String? = null,
  val majorResearch: String? = null,
  val resume: String? = null,
  val subjectCategory: String? = null,
  val idNumber: String? = null,
  val isAcademicLeader: Boolean? = null,
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
