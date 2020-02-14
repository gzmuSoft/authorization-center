package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.entity.Student
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/14 下午8:29
 */
class StudentHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/student/me")
      .handler {
        handlerGet<JsonObject, JsonObject>(it, Student.ADDRESS_STUDENT_ME, this::studentMe)
      }
  }

  /**
   * @api {GET} /student/me get students by user role
   * @apiVersion 1.0.0
   * @apiName StudentByRole
   * @apiDescription Get students by user role.
   * @apiGroup Student
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/student/me'
   *        --header 'Authorization: Bearer token'
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/student/me?classId=14'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Long}     [classId ]                 class id
   * @apiSuccessExample {json} Teacher - no class id:
   *      HTTP/1.1 200 OK
   *      [
   *          "info": "贵州民族大学 / 数据科学与信息工程学院 / 数信系 / 计算机科学专业",
   *          "classes": [
   *              {
   *                  "id": 10,
   *                  "name": "火箭班"
   *              }
   *          ]
   *      ]
   * @apiSuccessExample {json} Teacher - have class id:
   *      HTTP/1.1 200 OK
   *      [
   *          "info": "加利敦大学 / 肥宅学院 / 人才系 / 自闭专业 / 不想出门班"
   *          "students": [
   *              {
   *                  "gender": "男",
   *                  "no": "201742060006",
   *                  "image": null,
   *                  "name": "刘柏宏"
   *              }
   *          ]
   *      ]
   * @apiSuccessExample {json} User
   *      HTTP/1.1 200 OK
   *      [
   *          "info": "贵州民族大学 / 数据科学与信息工程学院 / 数信系 / 计算机科学专业",
   *          "students": [
   *              {
   *                  "gender": "男",
   *                  "no": "201742060006",
   *                  "image": null,
   *                  "name": "刘柏宏"
   *              }
   *          ]
   *      ]
   */
  private fun studentMe(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "name" to (context.request().getParam("name") ?: ""),
      "no" to (context.request().getParam("no") ?: ""),
      "classId" to (context.request().getParam("classId"))?.toLong(),
      "userId" to context.get<Long>("id"),
      "student" to context.user().principal().getBoolean("is_student")
    )

}
