package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.entity.SysUser
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/17 下午10:49
 */
class UserHandler(router: Router, private val eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/user/:id").handler { handlerGet<Long, JsonObject>(it, SysUser.ADDRESS_USER_ONE, this::userId) }
    router.patch("/user/password")
      .handler { Address.parameterHandler.requireJson(it, "id", "userId") }
      .handler { handlerPatch(it, SysUser.ADDRESS_USER_PASSWORD) }
    router.patch("/user")
      .handler { Address.parameterHandler.requireJson(it, "id", "name", "email", "phone", "roleIds") }
      .handler { handlerPatch(it, SysUser.ADDRESS_USER_UPDATE) }
    router.post("/user/exist").handler(this::userExist)
  }

  /**
   * @api {GET} /user/:id get user by id
   * @apiVersion 1.0.0
   * @apiName UserById
   * @apiDescription Get user by id.
   * @apiGroup User
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/user/4'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiParam {Long}     id              user id
   * @apiSuccess {Long}   id              id
   * @apiSuccess {String} name            name
   * @apiSuccess {String} image           image
   * @apiSuccess {String} avatar          avatar
   * @apiSuccess {String} email           email
   * @apiSuccess {String} phone           phone
   * @apiSuccess {Long}   sort            sort
   * @apiSuccessExample {json} Teacher - no class id:
   *      HTTP/1.1 200 OK
   *      [
   *        "id": 1,
   *        "name": "",
   *        "image": "",
   *        "avatar": "",
   *        "email": "",
   *        "phone": "",
   *        "sort": 1
   *      ]
   */
  private fun userId(context: RoutingContext): Long =
    context.request().getParam("id").toLong()
  /**
   * @api {PATCH} /user/password user reset password
   * @apiVersion 1.0.0
   * @apiName UserResetPassword
   * @apiDescription User reset password
   * @apiGroup User
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request PATCH 'http://127.0.0.1:8889/student' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "userId": ""
   *                    }'
   * @apiParam {Long}     id                     id
   * @apiParam {Long}     userId                 userId
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
  /**
   * @api {PATCH} /user user update
   * @apiVersion 1.0.0
   * @apiName UserUpdate
   * @apiDescription User update
   * @apiGroup User
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request PATCH 'http://127.0.0.1:8889/student' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "",
   *                      "email": "",
   *                      "phone": ""
   *                    }'
   * @apiParam {Long}       id                     id
   * @apiParam {String}     name                 name
   * @apiParam {String}     email                 email
   * @apiParam {String}     phone                 phone
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
  /**
   * @api {POST} /user/exist user exist by names
   * @apiVersion 1.0.0
   * @apiName UserExistByNames
   * @apiDescription User update
   * @apiGroup User
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/user/exist' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '["201607090211"]'
   * @apiParam {Array}       names                     names
   * @apiSuccessExample {json} Exists:
   *      HTTP/1.1 200 Success
   *      [
   *        {
   *          "id": 151,
   *          "name": "201607090211"
   *        }
   *      ]
   * @apiSuccessExample {json} Don't Exists:
   *      HTTP/1.1 200 Success
   *      []
   */
  private fun userExist(context: RoutingContext) {
    val names = context.bodyAsJsonArray
    eventBus.request<JsonObject>(SysUser.ADDRESS_USER_EXIST, names) {
      handleResult(context, it)
    }
  }
}
