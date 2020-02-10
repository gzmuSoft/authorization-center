package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Me
import cn.edu.gzmu.center.model.address.Address
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * User handler.
 * Only login user can access.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午5:46
 */
class MeHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {

  init {
    router.get("/me/routes").handler { handlerGet<JsonArray, JsonObject>(it,
      Me.ADDRESS_ROLE_ROUTES, this::routes) }
    router.get("/me/menu").handler { handlerGet<JsonArray, JsonObject>(it,
      Me.ADDRESS_ROLE_MENU, this::routes) }
    router.get("/me/info").handler { handlerGet<JsonObject, JsonObject>(it,
      Me.ADDRESS_ME_INFO, this::info) }
    router.patch("/me/user")
      .handler { Address.parameterHandler.requireJson(it, "name", "email", "phone") }
      .handler { Address.parameterHandler.equalsJson(it, "两次密码不一致", Pair("password", "rePassword")) }
      .handler { handlerPatch(it, Me.ADDRESS_ME_USER) }
    router.patch("/me/info")
      .handler { Address.parameterHandler.requireJson(it, "name") }
      .handler { handlerPatch(it, Me.ADDRESS_ME_INFO_UPDATE) }
  }

  /**
   * @api {GET} /me/routes user routes
   * @apiVersion 1.0.0
   * @apiName UserRoutes
   * @apiDescription Get current user routes about front. To set user's dynamic routing.
   * @apiGroup User
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/me/routes'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Boolean}   [name ]      route name, always true.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *      {
   *        "index": true,
   *      }
   */
  /**
   * @api {GET} /me/menu user menu
   * @apiVersion 1.0.0
   * @apiName UserMenu
   * @apiDescription Get current user menu about front. To set user's nav menu.
   * @apiGroup User
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/me/menu'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {JsonArray}  menus      user menus.
   * @apiSuccessExample {json} Success-Response:
   *      HTTP/1.1 200 OK
   *      {
   *        "menus": [
   *          "name": "menu.system",
   *          "children": [
   *            {
   *              "text": "menu.dashboard",
   *              "icon": "mdi-view-dashboard",
   *              "remark": "menu.system"
   *            }
   *          ]
   *        ]
   *      }
   */
  private fun routes(context: RoutingContext): JsonArray =
    context.user().principal().getJsonArray("authorities")

  /**
   * @api {GET} /me/info user info
   * @apiVersion 1.0.0
   * @apiName UserInfo
   * @apiDescription Get current user info, student or user.
   * @apiGroup User
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/me/info'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccessExample {json} Student:
   *      HTTP/1.1 200 OK
   *      {
   *          "id": 2,
   *          "name": "林国瑞",
   *          "spell": "lín guó ruì ",
   *          "userId": 2,
   *          "schoolId": 1,
   *          "collegeId": 2,
   *          "depId": 45,
   *          "specialtyId": 48,
   *          "classesId": 49,
   *          "no": "201742060002",
   *          "gender": "男",
   *          "idNumber": "522526202002050002",
   *          "birthday": "2020-02-05",
   *          "enterDate": "2017-09-01",
   *          "academic": "本科",
   *          "graduationDate": "2021-06-30",
   *          "graduateInstitution": "无",
   *          "originalMajor": "无",
   *          "resume": "我是一个学生",
   *          "sort": 19,
   *          "createUser": "yms",
   *          "createTime": "2019-08-07 16:40:28",
   *          "modifyUser": "yms",
   *          "modifyTime": "2019-08-07 16:40:28",
   *          "remark": "这是一个学生",
   *          "isEnable": true
   *      }
   * @apiSuccessExample {json} Teacher:
   *      HTTP/1.1 200 OK
   *      {
   *          "id": 3,
   *          "name": "李开富",
   *          "spell": "lǐ kāi fù ",
   *          "userId": 3,
   *          "schoolId": 1,
   *          "collegeId": 2,
   *          "depId": 3,
   *          "gender": "男",
   *          "birthday": "2020-02-05",
   *          "graduationDate": "2020-06-01",
   *          "profTitleAssDate": null,
   *          "workDate": null,
   *          "nation": "China",
   *          "degree": null,
   *          "academic": "博士",
   *          "major": "图书管理学专业",
   *          "profTitle": null,
   *          "graduateInstitution": "工程管理学院",
   *          "majorResearch": "混日子",
   *          "resume": "这是一位教师",
   *          "subjectCategory": null,
   *          "idNumber": "522501194910010007",
   *          "isAcademicLeader": false,
   *          "sort": 89,
   *          "createUser": "yms",
   *          "createTime": "2020-01-18 10:22:07",
   *          "modifyUser": "yms",
   *          "modifyTime": "2020-01-18 10:22:07",
   *          "remark": "这是一位大学教师",
   *          "isEnable": true
   *      }
   */
  private fun info(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "student" to context.user().principal().getBoolean("is_student"),
      "teacher" to context.user().principal().getBoolean("is_teacher"),
      "id" to context.get<Long>("id")
    )

  /**
   * @api {PATCH} /me/user update user
   * @apiVersion 1.0.0
   * @apiName UserUpdate
   * @apiDescription Update user info.
   * @apiGroup User
   * @apiExample Example usage:
   *  curl --location --request PATCH 'http://127.0.0.1:8889/me/user' \
   *      --header 'Content-Type: application/json' \
   *      --header 'Authorization: Bearer ......' \
   *      --data-raw '{
   *          "email": "...",
   *          "phone": "...",
   *         "password": "",
   *         "rePassword": ""
   *      }'
   * @apiUse Bearer
   * @apiParam {String} email           email.
   * @apiParam {String} phone           phone.
   * @apiParam {String} [password ]     password.
   * @apiParam {String} [rePassword ]   rePassword.
   * @apiSuccessExample {json} Success-Response:
   *          HTTP/1.1 204 No Content
   */
  /**
   * @api {PATCH} /me/info update user info
   * @apiVersion 1.0.0
   * @apiName UserUpdateInfo
   * @apiDescription Update user entity info. Table: student or teacher.
   * @apiGroup User
   * @apiExample Example usage:
   *  curl --location --request PATCH 'http://127.0.0.1:8889/me/user' \
   *      --header 'Content-Type: application/json' \
   *      --header 'Authorization: Bearer ......' \
   *      --data-raw '{
   *          "name": "..."
   *      }'
   * @apiUse Bearer
   * @apiParam {String} name                      name.
   * @apiParam {String} [no ]                     no.
   * @apiParam {String} [gender ]                 gender.
   * @apiParam {String} [nation ]                 nation.
   * @apiParam {String} [idNumber ]               idNumber.
   * @apiParam {String} [birthday ]               birthday.
   * @apiParam {String} [academic ]               academic.
   * @apiParam {String} [graduationDate ]         graduationDate.
   * @apiParam {String} [graduateInstitution ]    graduateInstitution.
   * @apiParam {String} [originalMajor ]          originalMajor.
   * @apiParam {String} [resume ]                 resume.
   * @apiSuccessExample {json} Success-Response:
   *          HTTP/1.1 204 No Content
   */

}
