package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.entity.Student
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
 * @date 2020/2/14 下午8:29
 */
class StudentHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/student/me")
      .handler {
        handlerGet<JsonObject, JsonObject>(it, Student.ADDRESS_STUDENT_ME, this::studentMe)
      }
    router.patch("/student")
      .handler {
        Address.parameterHandler.requireJson(
          it, "id", "name", "no", "gender", "enterDate", "birthday", "idNumber"
        )
      }
      .handler { handlerPatch(it, Student.ADDRESS_STUDENT_UPDATE) }
  }

  /**
   * @api {GET} /student/me get students by user role
   * @apiVersion 1.0.0
   * @apiName StudentByRole
   * @apiDescription Get students by user role.
   * If the user has /student/\* GET permission, it will give all information.
   * If the user has /student/ PATCH permission, it will give a edit filed is true in every entity.
   * @apiGroup Student
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/student/me'
   *        --header 'Authorization: Bearer token'
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/student/me?classId=14'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiParam {Long}     [classId ]                 class id
   * @apiSuccess {String} info          class or spacial info
   * @apiSuccess {Array} classes         If the teacher do not give classId
   * @apiSuccess {Array} students        If the student get
   * @apiSuccess {boolean} view          every entity has this field. The user has complete info.
   * @apiSuccess {boolean} edit          every entity has this field. The user has edit permission.
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
   *                  "name": "刘柏宏",
   *                  "view": false
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
   *                  "name": "刘柏宏",
   *                  "view": false
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
      "student" to context.user().principal().getBoolean("is_student"),
      "resource" to context.get<JsonArray>("resource")
    )
  /**
   * @api {PATCH} /student student simple update
   * @apiVersion 1.0.0
   * @apiName StudentSimpleUpdate
   * @apiDescription Update student. Res have three types.
   * @apiGroup Student
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request PATCH 'http://127.0.0.1:8889/student' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test",
   *                      "no": "",
   *                      "gender": "",
   *                      "enterDate": "",
   *                      "birthday": "",
   *                      "idNumber": ""
   *                    }'
   * @apiParam {Long}     id                     data id
   * @apiParam {String}   name                   data name
   * @apiParam {String}   no                     data name
   * @apiParam {String}   gender                 data name
   * @apiParam {String}   enterDate              data name
   * @apiParam {String}   birthday               data name
   * @apiParam {String}   idNumber               data name
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
}
