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
  companion object {
    private const val RESOURCE = "/student"
  }
  init {
    router.get("$RESOURCE/me")
      .handler {
        handlerGet<JsonObject, JsonObject>(it, Student.ADDRESS_STUDENT_ME, this::studentMe)
      }
    router.patch(RESOURCE)
      .handler {
        Address.parameterHandler.requireJson(
          // Only update these fields.
          it, "id", "name", "no", "gender", "enterDate", "birthday", "idNumber", "isEnable"
        )
      }
      .handler { handlerPatch(it, Student.ADDRESS_STUDENT_UPDATE) }
    router.get(RESOURCE)
      .handler { handlerPage(it, Student.ADDRESS_STUDENT_PAGE, this::studentPage) }
    router.patch("$RESOURCE/complete")
      .handler {
        Address.parameterHandler.requireJson(
          it, "id", "name", "no", "gender", "enterDate", "birthday", "idNumber",
          "schoolId", "collegeId", "depId", "specialtyId", "classesId", "sort", "remark"
        )
      }
      .handler { handlerPatch(it, Student.ADDRESS_STUDENT_UPDATE_COMPLETE) }
    router.post(RESOURCE)
      .handler {
        Address.parameterHandler.requireJson(
          it, "name", "no", "gender", "schoolId", "collegeId", "depId", "specialtyId", "classesId", "sort"
        )
      }
      .handler { handlerCreate(it, Student.ADDRESS_STUDENT_ADD) }
    router.post("$RESOURCE/import")
      .handler { handlerCreate(it, Student.ADDRESS_STUDENT_IMPORT) }
  }
  /**
   * @api {GET} /student get students page
   * @apiVersion 1.0.0
   * @apiName StudentPage
   * @apiDescription Get students page
   * @apiGroup Student
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/student/me'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiParam {String}     [name ]               name
   * @apiParam {String}     [no ]                 no
   * @apiParam {String}     [enterDate ]          enter date
   * @apiParam {String}     [gender ]             gender -> 男 or 女
   * @apiParam {Long}       [nation ]             sys data 9
   * @apiParam {Long}       [academic ]           sys data 6
   * @apiParam {Long}       [schoolId ]           sys data 0
   * @apiParam {Long}       [collegeId ]          sys data 1
   * @apiParam {Long}       [depId ]              sys data 2
   * @apiParam {Long}       [specialtyId ]        sys data 3
   * @apiParam {Long}       [classesId ]          sys data 4
   * @apiParam {Boolean}    [isEnable ]           enable
   * @apiSuccess {Array} content                  page
   * @apiSuccess {Long} itemsLength               element number
   * @apiSuccess {boolean} userView               every entity has this field. Whether users can view user information.
   * @apiSuccess {boolean} userEdit               every entity has this field. Whether users can edit user information.
   * @apiSuccess {boolean} edit                   every entity has this field. Whether users can edit student information.
   * @apiSuccess {boolean} resetPassword          every entity has this field. Whether users can reset user password.
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
   */
  private fun studentPage(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "name" to (context.request().getParam("name") ?: ""),
      "no" to (context.request().getParam("no") ?: ""),
      "gender" to (context.request().getParam("gender")),
      "enterDate" to (context.request().getParam("enterDate")),
      "nation" to (context.request().getParam("nation")?.toLong()),
      "academic" to (context.request().getParam("academic")?.toLong()),
      "schoolId" to (context.request().getParam("schoolId")?.toLong()),
      "collegeId" to (context.request().getParam("collegeId")?.toLong()),
      "depId" to (context.request().getParam("depId")?.toLong()),
      "specialtyId" to (context.request().getParam("specialtyId")?.toLong()),
      "classesId" to (context.request().getParam("classesId")?.toLong()),
      "isEnable" to (context.request().getParam("isEnable")?.toBoolean() ?: true),
      "resource" to context.get<JsonArray>("resource")
    )

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
   * @apiParam {Long}     id                   id
   * @apiParam {String}   name                 name
   * @apiParam {String}   no                   no
   * @apiParam {String}   gender               gender
   * @apiParam {String}   enterDate            enter date
   * @apiParam {String}   birthday             birthday
   * @apiParam {String}   idNumber             id number
   * @apiParam {String}   isEnable             is enable
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
  /**
   * @api {PATCH} /student/complete student complete update
   * @apiVersion 1.0.0
   * @apiName StudentCompleteUpdate
   * @apiDescription Update student.
   * @apiGroup Student
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request PATCH 'http://127.0.0.1:8889/student/complete' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test",
   *                      "no": "",
   *                      "gender": "",
   *                      "enterDate": "",
   *                      "nation": 1,
   *                      "academic": 1,
   *                      "schoolId": 1,
   *                      "collegeId": 1,
   *                      "depId": 1,
   *                      "specialtyId": 1,
   *                      "classesId": 1,
   *                      "sort": 1,
   *                      "remark": 1,
   *                      "birthday": "",
   *                      "idNumber": ""
   *                    }'
   * @apiParam {Long}     id                   id
   * @apiParam {String}   name                 name
   * @apiParam {String}   no                   no
   * @apiParam {String}   gender               gender
   * @apiParam {String}   enterDate            enter date
   * @apiParam {String}   birthday             birthday
   * @apiParam {String}   idNumber             id number
   * @apiParam {String}   academic             academic
   * @apiParam {String}   schoolId             school id
   * @apiParam {String}   collegeId            college id
   * @apiParam {String}   depId                department id
   * @apiParam {String}   specialtyId          specialty id
   * @apiParam {String}   classesId            classes id
   * @apiParam {String}   sort                 sort
   * @apiParam {String}   remark               remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
  /**
   * @api {POST} /student/import student import
   * @apiVersion 1.0.0
   * @apiName StudentImport
   * @apiDescription Import student.
   * @apiGroup Student
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/student/import' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{content: [
   *                      "name": "test",
   *                      "no": "20141414",
   *                      "gender": "",
   *                      "enterDate": "",
   *                      "nation": 1,
   *                      "academic": 1,
   *                      "schoolId": 1,
   *                      "collegeId": 1,
   *                      "depId": 1,
   *                      "specialtyId": 1,
   *                      "classesId": 1,
   *                      "sort": 1,
   *                      "remark": 1,
   *                      "birthday": "",
   *                      "idNumber": ""
   *                    ]}'
   * @apiParam {Array}        content                 students
   * @apiParam {JsonObject}   config                  config
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
  /**
   * @api {POST} /student student add
   * @apiVersion 1.0.0
   * @apiName StudentAdd
   * @apiDescription Add student.
   * @apiGroup Student
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/student' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "name": "test",
   *                      "no": "20141414",
   *                      "gender": "",
   *                      "enterDate": "",
   *                      "nation": 1,
   *                      "academic": 1,
   *                      "schoolId": 1,
   *                      "collegeId": 1,
   *                      "depId": 1,
   *                      "specialtyId": 1,
   *                      "classesId": 1,
   *                      "sort": 1,
   *                      "remark": 1,
   *                      "birthday": "",
   *                      "idNumber": ""
   *                    }'
   * @apiParam {String}   name                 name
   * @apiParam {String}   no                   no
   * @apiParam {String}   gender               gender
   * @apiParam {String}   enterDate            enter date
   * @apiParam {String}   birthday             birthday
   * @apiParam {String}   idNumber             id number
   * @apiParam {String}   academic             academic
   * @apiParam {String}   schoolId             school id
   * @apiParam {String}   collegeId            college id
   * @apiParam {String}   depId                department id
   * @apiParam {String}   specialtyId          specialty id
   * @apiParam {String}   classesId            classes id
   * @apiParam {String}   sort                 sort
   * @apiParam {String}   remark               remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
}
