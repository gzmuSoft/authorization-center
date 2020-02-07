package cn.edu.gzmu.center.me

import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ROLE_ROUTES
import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ROLE_MENU
import cn.edu.gzmu.center.model.DatabaseException
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

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
    eventBus.request<JsonObject>(address, roles) {
      if (it.failed()) context.fail(DatabaseException(it.cause().localizedMessage))
      context.response()
        .setStatusCode(HttpResponseStatus.OK.code())
        .end(it.result().body().toString())
    }
  }

}
