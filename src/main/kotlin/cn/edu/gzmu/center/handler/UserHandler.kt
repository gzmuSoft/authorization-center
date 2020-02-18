package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
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
class UserHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/user/:id").handler { handlerGet<Long, JsonObject>(it, SysUser.ADDRESS_USER_ONE, this::userId) }
  }

  private fun userId(context: RoutingContext): Long =
    context.request().getParam("id").toLong()
}
