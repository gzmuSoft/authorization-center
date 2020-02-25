package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.entity.AuthCenterRes
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 上午12:14
 */
class ResHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/res")
      // Must have these params.
      .handler { Address.parameterHandler.requireParam(it, "page", "describe", "type") }
      .handler { handlerPage(it, AuthCenterRes.ADDRESS_RES, this::res) }
    router.patch("/res")
      .handler { Address.parameterHandler.requireJson(it, "id") }
      .handler { handlerPatch(it, AuthCenterRes.ADDRESS_RES_UPDATE) }
    router.delete("/res/:id")
      .handler { handlerDelete(it, AuthCenterRes.ADDRESS_RES_DELETE) }
    router.post("/res")
      .handler { handlerCreate(it, AuthCenterRes.ADDRESS_RES_CREATE) }
  }

  /**
   * @api {GET} /res res page
   * @apiVersion 1.0.0
   * @apiName ResPage
   * @apiDescription Get res page
   * @apiGroup Res
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/res?sort=id&page=1&size=5&describe=&type=0' \
   *             --header 'Authorization: Bearer ......'
   * @apiUse Bearer
   * @apiSuccess {Array}    content              page
   * @apiSuccess {Long}     itemsLength          all count
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      {
   *        "content": [
   *            {"id": 1, "name": "...", "describe": "...", "method": "...", "remark": "...", "sort": 1},
   *            {"id": 2, "name": "...", "describe": "...", "method": "...", "remark": "...", "sort": 1},
   *            {"id": 3, "name": "...", "describe": "...", "method": "...", "remark": "...", "sort": 1},
   *            {"id": 4, "name": "...", "describe": "...", "method": "...", "remark": "...", "sort": 1},
   *            {"id": 5, "name": "...", "describe": "...", "method": "...", "remark": "...", "sort": 1},
   *            {"id": 6, "name": "...", "describe": "...", "method": "...", "remark": "...", "sort": 1}
   *        ],
   *        "itemsLength": 6
   *      }
   */
  private fun res(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "describe" to (context.request().getParam("describe") ?: ""),
      // 0 is all, 1 is menu, 2 is res, 3 is route
      "type" to context.request().getParam("type").toLong()
    )
  /**
   * @api {DELETE} /res/:id res delete
   * @apiVersion 1.0.0
   * @apiName ResDelete
   * @apiDescription Get sys data by type.
   * @apiGroup Res
   * @apiExample Example usage:
   *      curl --location --request DELETE 'http://127.0.0.1:8889/res/1'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */

  /**
   * @api {PATCH} /res auth center res update
   * @apiVersion 1.0.0
   * @apiName ResUpdate
   * @apiDescription Update res. Res have three types.
   *     1. Get menu —— name is route, url is menu name, method is menu icon, remark is mark
   *     2. Get resource —— name and remark is null
   *     3. Get route —— name is route, url is null, others is default.
   *     Other values will get all resource.
   * @apiGroup Res
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request PATCH 'http://127.0.0.1:8889/role' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test"
   *                    }'
   * @apiParam {Long}     id                    data id
   * @apiParam {Long}     type                  data type
   * @apiParam {String}   [name ]               data name
   * @apiParam {String}   [url ]                url
   * @apiParam {String}   [describe ]           describe
   * @apiParam {String}   [method ]             method
   * @apiParam {String}   [sort ]               sort
   * @apiParam {String}   [remark ]             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */

  /**
   * @api {POST} /res add auth center
   * @apiVersion 1.0.0
   * @apiName ResAdd
   * @apiDescription Update res. Res have three types.
   *     1. Add menu —— name is route, url is menu name, method is menu icon, remark is mark
   *     2. Add resource —— name and remark is null
   *     3. Add route —— name is route, url is null, others is default.
   *     Other values will get all resource.
   * @apiGroup Res
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/res' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test"
   *                    }'
   * @apiParam {Long}     id                    data id
   * @apiParam {Long}     type                  data type
   * @apiParam {String}   [name ]               data name
   * @apiParam {String}   [url ]                url
   * @apiParam {String}   [describe ]           describe
   * @apiParam {String}   [method ]             method
   * @apiParam {String}   [sort ]               sort
   * @apiParam {String}   [remark ]             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 201 Created
   */
}
