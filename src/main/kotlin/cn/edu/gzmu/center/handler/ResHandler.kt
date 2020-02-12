package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.address.Res
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
      .handler { Address.parameterHandler.requireParam(it, "page", "size", "describe", "type") }
      .handler { handlerPage(it, Res.ADDRESS_RES, this::res) }
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

}
