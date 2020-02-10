package cn.edu.gzmu.center.base

import cn.edu.gzmu.center.model.BadRequestException
import cn.edu.gzmu.center.model.extension.handleResult
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * Basic query.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/8 下午1:06
 */
class BaseHandler(router: Router, private val eventBus: EventBus) {
  init {
    router.get("/base/sysData/type/:type").handler(::dataType)
    router.get("/base/sysData/types").handler(::dataTypes)
    router.get("/base/sysData/info/:id").handler(::dataInfo)
    router.get("/base/user/exist").handler(::userExist)
  }

  /**
   * @api {GET} /base/sysData/type/:type sysData query
   * @apiVersion 1.0.0
   * @apiName SysDataByType
   * @apiDescription Get sys data by type.
   * @apiGroup Base
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/base/sysData/type/1'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Long}     id       data id
   * @apiSuccess {String}   name     data name
   * @apiSuccess {String}   brief    data brief
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      [
   *        { "id": 1, "name": "...", "brief": "...." },
   *        { "id": 2, "name": "...", "brief": "...." },
   *        { "id": 3, "name": "...", "brief": "...." }
   *      ]
   */
  private fun dataType(context: RoutingContext) {
    val type = context.request().getParam("type").toLong()
    eventBus.request<JsonArray>(Base.ADDRESS_SYS_DATA_TYPE, type) { handleResult(context, it) }
  }

  /**
   * @api {GET} /base/sysData/types sysData query by types
   * @apiVersion 1.0.0
   * @apiName SysDataByTypes
   * @apiDescription Get sys data by types.
   * @apiGroup Base
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/base/sysData/types'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Long}     id       data id
   * @apiSuccess {String}   name     data name
   * @apiSuccess {String}   brief    data brief
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      [
   *          { "1": [
   *             { "id": 1, "name": "...", "brief": "...." },
   *             { "id": 2, "name": "...", "brief": "...." },
   *             { "id": 3, "name": "...", "brief": "...." }
   *          ]}
   *      ]
   */
  private fun dataTypes(context: RoutingContext) {
    val types = context.request().getParam("types").split(",").map { it.toLong() }
    eventBus.request<JsonObject>(Base.ADDRESS_SYS_DATA_TYPES, JsonArray(types)) { handleResult(context, it) }
  }

  /**
   * @api {GET} /base/sysData/info/:id sysData query
   * @apiVersion 1.0.0
   * @apiName SysDataByType
   * @apiDescription Get sys data name by id, it will find parent name.
   * @apiGroup Base
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/base/sysData/info/4'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {String}   name       data name and parent name
   * @apiSuccess {String}   result     data simple entity, order by type.
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      {
   *        "name": "贵州民族大学 / 数信学院 / 软件工程 / 2班",
   *        "result": [
   *          { "id": 1, "name": "贵州民族大学", "type": 0, "parentId":0},
   *          { "id": 2, "name": "数信学院", "type": 1, "parentId":1},
   *          { "id": 3, "name": "软件工程", "type": 2, "parentId":2},
   *          { "id": 4, "name": "2班", "type": 3, "parentId":3}
   *        ]
   *      }
   */
  private fun dataInfo(context: RoutingContext) {
    val id = context.request().getParam("id").toLong()
    eventBus.request<Long>(Base.ADDRESS_SYS_DATA_NAME, id) { handleResult(context, it) }
  }

  /**
   * @api {GET} /base/user/exist user exist
   * @apiVersion 1.0.0
   * @apiName UserExist
   * @apiDescription Find user already exist by on condition.
   * @apiGroup Base
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/base/user/exist?name=123'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {String}   [name ]      if exist, find by name, order 1.
   * @apiSuccess {String}   [email ]     if exist, find by name, order 2.
   * @apiSuccess {String}   [phone ]     if exist, find by name, order 3.
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      { “exist”, true }
   */
  private fun userExist(context: RoutingContext) {
    val name = context.request().getParam("name") ?: ""
    val email = context.request().getParam("email") ?: ""
    val phone = context.request().getParam("phone") ?: ""
    if (name.isBlank() && email.isBlank() && phone.isBlank())
      context.fail(BadRequestException())
    else
      eventBus.request<JsonObject>(
        Base.ADDRESS_SYS_USER_EXIST, jsonObjectOf(
          "name" to name, "email" to email, "phone" to phone
        )
      ) { handleResult(context, it) }
  }
}
