package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.address.Role
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

/**
 * Role Handler.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/11 下午12:13
 */
class RoleHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/role/parent/:parentId")
      .handler { handlerGet<Long, JsonArray>(it, Role.ADDRESS_ROLE_PARENT, this::roleParent) }
    router.get("/role/res/:id")
      .handler { handlerGet<Long, JsonArray>(it, Role.ADDRESS_ROLE_RES, this::roleRes) }
    router.patch("/role")
      .handler { Address.parameterHandler.requireJson(it, "id") }
      .handler { handlerPatch(it, Role.ADDRESS_ROLE_UPDATE) }
  }

  /**
   * @api {GET} /role/parent/:parentId role by parent id
   * @apiVersion 1.0.0
   * @apiName RoleByParent
   * @apiDescription Get sys role by parent.
   * @apiGroup Role
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/role/parent/0'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Long}     id                 data id
   * @apiSuccess {String}   name               data name
   * @apiSuccess {String}   des                data description
   * @apiSuccess {String}   iconCls            role icon
   * @apiSuccess {String}   sort               role sort
   * @apiSuccess {String}   createUser         create user
   * @apiSuccess {String}   createTime         create Time
   * @apiSuccess {String}   modifyUser         modify user
   * @apiSuccess {String}   modifyTime         modify time
   * @apiSuccess {String}   remark             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      [
   *        {
   *          "id": 1, "name": "...", "des": "....", "iconCls": "...",
   *          "sort": 1, "createUser": "...", "createTime": "...",
   *          "modifyUser": "...", "modifyTime": "...", "remark": "..."
   *        },
   *        {
   *          "id": 2, "name": "...", "des": "....", "iconCls": "...",
   *          "sort": 1, "createUser": "...", "createTime": "...",
   *          "modifyUser": "...", "modifyTime": "...", "remark": "..."
   *        },
   *        {
   *          "id": 3, "name": "...", "des": "....", "iconCls": "...",
   *          "sort": 1, "createUser": "...", "createTime": "...",
   *          "modifyUser": "...", "modifyTime": "...", "remark": "..."
   *        },
   *      ]
   */
  private fun roleParent(context: RoutingContext): Long = context.request().getParam("parentId").toLong()

  /**
   * @api {GET} /role/res/:id auth center res by role id
   * @apiVersion 1.0.0
   * @apiName ResByRoleId
   * @apiDescription Get sys role by parent.
   * @apiGroup Role
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/role/res/0'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Long}     id                 data id
   * @apiSuccess {String}   name               data name
   * @apiSuccess {String}   url                url
   * @apiSuccess {String}   describe           describe
   * @apiSuccess {String}   remark             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      [
   *        { "id": 1, "name": "", url: "", "describe": "" },
   *        { "id": 2, "name": "", url: "", "describe": "" },
   *        { "id": 3, "name": "", url: "", "describe": "" },
   *        { "id": 4, "name": "", url: "", "describe": "" }
   *      ]
   */
  private fun roleRes(context: RoutingContext): Long = context.request().getParam("id").toLong()

  /**
   * @api {PATCH} /role auth center res update
   * @apiVersion 1.0.0
   * @apiName RoleUpdate
   * @apiDescription Update role
   * @apiGroup Role
   * @apiUse Bearer
   * @apiParam {Long}     id                 data id
   * @apiParam {String}   name               data name
   * @apiParam {String}   url                url
   * @apiParam {String}   describe           describe
   * @apiParam {String}   remark             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 OK
   */
}
