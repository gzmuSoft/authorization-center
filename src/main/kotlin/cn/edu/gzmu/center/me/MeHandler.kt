package cn.edu.gzmu.center.me

import cn.edu.gzmu.center.me.Me.Companion.ADDRESS_ROLE_ROUTES
import cn.edu.gzmu.center.model.DatabaseException
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

/**
 *
 *  User handler.
 * Only login user can access.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午5:46
 */
class MeHandler(router: Router, private val eventBus: EventBus) {

  init {
    router.get("/me/routes").handler(::routes)
  }

  /**
   * Get current user routes about front.
   * To set user's dynamic routing
   */
  private fun routes(context: RoutingContext) {
    val roles = context.user().principal().getJsonArray("authorities")
    eventBus.request<JsonObject>(ADDRESS_ROLE_ROUTES, roles) {
      if (it.failed()) context.fail(DatabaseException(it.cause().localizedMessage))
      context.response()
        .setStatusCode(HttpResponseStatus.OK.code())
        .end(it.result().body().toString())
    }
  }

}
