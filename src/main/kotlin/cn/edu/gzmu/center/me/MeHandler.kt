package cn.edu.gzmu.center.me

import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ME_INFO
import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ROLE_ROUTES
import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ROLE_MENU
import cn.edu.gzmu.center.model.extension.Address
import cn.edu.gzmu.center.model.extension.handleNoResult
import cn.edu.gzmu.center.model.extension.handleResult
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.eventbus.EventBus
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
class MeHandler(router: Router, private val eventBus: EventBus) {

  init {
    router.get("/me/routes").handler { this.routes(it, ADDRESS_ROLE_ROUTES) }
    router.get("/me/menu").handler { this.routes(it, ADDRESS_ROLE_MENU) }
    router.get("/me/info").handler(::info)
    router.patch("/me/user")
      .handler { Address.parameterHandler.requireJson(it, "name", "email", "phone") }
      .handler { Address.parameterHandler.equalsJson(it, "两次密码不一致", Pair("password", "rePassword")) }
      .handler(::user)
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
  private fun routes(context: RoutingContext, address: String) {
    val roles = context.user().principal().getJsonArray("authorities")
    eventBus.request<JsonObject>(address, roles) { handleResult(context, it) }
  }

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
  private fun info(context: RoutingContext) {
    val student = context.user().principal().getBoolean("is_student")
    val teacher = context.user().principal().getBoolean("is_teacher")
    val id = context.get<Long>("id")
    eventBus.request<JsonObject>(
      ADDRESS_ME_INFO, jsonObjectOf("student" to student, "teacher" to teacher, "id" to id)
    ) { handleResult(context, it) }
  }

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
  private fun user(context: RoutingContext) {
    val user = context.bodyAsJson
    user.put("id", context.get<Long>("id"))
    user.put("username", context.get<String>("username"))
    eventBus.request<Unit>(Me.ADDRESS_ME_USER, user) {
      handleNoResult(context, HttpResponseStatus.NO_CONTENT, it)
    }
  }
}
