package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.OauthHelper
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/22 下午4:40
 */
class TeacherHandlerTest : OauthHelper() {

  @Test
  internal fun `Get teacher page when passed`(testContext: VertxTestContext) {
    client.get("/teacher")
      .send {
        pageCheck(testContext, it)
      }
  }

  @Test
  internal fun `Update teacher when passed`(testContext: VertxTestContext) {
    client.patch("/teacher")
      .sendJsonObject(
        jsonObjectOf(
          "id" to 4,
          "name" to "王子久",
          "schoolId" to 29,
          "collegeId" to 2,
          "depId" to 40,
          "gender" to "女",
          "birthday" to "2020-02-05",
          "graduationDate" to "2020-06-01",
          "workDate" to "2020-02-21",
          "nation" to 57,
          "degree" to 115,
          "academic" to 9,
          "major" to "土木工程专业",
          "profTitle" to 123,
          "profTitleAssDate" to null,
          "graduateInstitution" to "材料科学与工程学院",
          "majorResearch" to "混日子",
          "subjectCategory" to null,
          "idNumber" to "522501194910010008",
          "isAcademicLeader" to false,
          "sort" to 15,
          "remark" to "这是一位大学教师",
          "isEnable" to true
        )
      ) {
        noContentCheck(testContext, it)
      }
  }

  @Test
  internal fun `Add teacher when passed`(testContext: VertxTestContext) {
    client.post("/teacher")
      .sendJsonObject(
        jsonObjectOf(
          "name" to "王子久",
          "schoolId" to 29,
          "collegeId" to 2,
          "depId" to 40,
          "gender" to "女",
          "birthday" to "2020-02-05",
          "graduationDate" to "2020-06-01",
          "workDate" to "2020-02-21",
          "nation" to 57,
          "degree" to 115,
          "academic" to 9,
          "major" to "土木工程专业",
          "profTitle" to 123,
          "profTitleAssDate" to null,
          "graduateInstitution" to "材料科学与工程学院",
          "majorResearch" to "混日子",
          "subjectCategory" to null,
          "idNumber" to "522501194910010008",
          "isAcademicLeader" to false,
          "sort" to 15,
          "remark" to "这是一位大学教师",
          "isEnable" to true,
          "phone" to "1341234${(1000..9999).random()}",
          "email" to "1341234${(1000..9999).random()}@qq.com"
        )
      ) {
        createCheck(testContext, it)
      }
  }


  @Test
  internal fun `Import teacher when passed`(testContext: VertxTestContext) {
    val data = """
      {
        "content":[
          {
            "name": "王子久",
            "schoolId": 29,
            "collegeId": 2,
            "depId": 40,
            "gender": "女",
            "birthday": "2020-02-05",
            "graduationDate": "2020-06-01",
            "workDate": "2020-02-21",
            "nation": 57,
            "degree": 115,
            "academic": 9,
            "major": "土木工程专业",
            "profTitle": 123,
            "profTitleAssDate": null,
            "graduateInstitution": "材料科学与工程学院",
            "majorResearch": "混日子",
            "subjectCategory": null,
            "idNumber": "522501194910010008",
            "isAcademicLeader": false,
            "sort": 15,
            "remark": "这是一位大学教师",
            "isEnable": true,
            "phone": "134${(10000000..99999999).random()}",
            "email": "134${(10000000..99999999).random()}@qq.com"
          }
        ]
      }
    """.trimIndent()
    val param = JsonObject(data)
    client.post("/teacher/import")
      .sendJsonObject(param) {
        createCheck(testContext, it)
      }
  }
}
