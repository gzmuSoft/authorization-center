package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.address.College
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 下午9:23
 */
class CollegeHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/college/type/:id")
      .handler { handlerGet<Long, JsonArray>(it, College.ADDRESS_COLLEGE_TYPE, this::collegeParamId) }
    router.get("/college/parent/:id")
      .handler { handlerGet<Long, JsonArray>(it, College.ADDRESS_COLLEGE_PARENT, this::collegeParamId) }
    router.patch("/college")
      .handler { Address.parameterHandler.requireJson(it, "id", "isEnable") }
      .handler { handlerPatch(it, College.ADDRESS_COLLEGE_UPDATE) }
    router.post("/college")
      .handler { Address.parameterHandler.requireJson(it, "name", "type", "parentId") }
      .handler { handlerCreate(it, College.ADDRESS_COLLEGE_CREATE) }
  }

  private fun collegeParamId(context: RoutingContext): Long =
    context.request().getParam("id").toLong()

}
