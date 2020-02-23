package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.entity.Teacher
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/22 下午2:28
 */
class TeacherHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  companion object {
    private const val RESOURCE = "/teacher"
  }
  init {
    router.get(RESOURCE)
      .handler { handlerPage(it, Teacher.ADDRESS_TEACHER_PAGE, this::teacherPage) }
    router.patch(RESOURCE)
      .handler {
        Address.parameterHandler.requireJson(
          it, "id", "name", "schoolId",
          "collegeId", "depId", "gender", "birthday", "graduationDate", "workDate", "nation",
          "degree", "academic", "major", "profTitle", "profTitleAssDate", "graduateInstitution",
          "majorResearch", "subjectCategory", "idNumber", "isAcademicLeader", "sort", "remark", "isEnable"
        )
      }
      .handler { handlerPatch(it, Teacher.ADDRESS_TEACHER_UPDATE) }
    router.post(RESOURCE)
      .handler {
        Address.parameterHandler.requireJson(
          it, "name", "schoolId", "collegeId", "depId", "gender",
          "idNumber", "isEnable", "phone", "email"
        )
      }
      .handler { handlerCreate(it, Teacher.ADDRESS_TEACHER_ADD) }
    router.post("${RESOURCE}/import")
      .handler { handlerCreate(it, Teacher.ADDRESS_TEACHER_IMPORT) }
  }

  private fun teacherPage(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "name" to (context.request().getParam("name") ?: ""),
      "gender" to (context.request().getParam("gender")),
      "workDate" to (context.request().getParam("workDate")),
      "nation" to (context.request().getParam("nation")?.toLong()),
      "academic" to (context.request().getParam("academic")?.toLong()),
      "degree" to (context.request().getParam("degree")?.toLong()),
      "profTitle" to (context.request().getParam("profTitle")?.toLong()),
      "schoolId" to (context.request().getParam("schoolId")?.toLong()),
      "collegeId" to (context.request().getParam("collegeId")?.toLong()),
      "depId" to (context.request().getParam("depId")?.toLong()),
      "isEnable" to (context.request().getParam("isEnable")?.toBoolean() ?: true),
      "resource" to context.get<JsonArray>("resource")
    )
}
