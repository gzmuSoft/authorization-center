package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.address.Data
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 下午9:23
 */
class DataHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/data/type/:id")
      .handler { handlerGet<Long, JsonArray>(it, Data.ADDRESS_DATA_TYPE, this::dataParamId) }
    router.get("/data/parent/:id")
      .handler { handlerGet<Long, JsonArray>(it, Data.ADDRESS_DATA_PARENT, this::dataParamId) }
    router.patch("/data")
      .handler { Address.parameterHandler.requireJson(it, "id", "isEnable") }
      .handler { handlerPatch(it, Data.ADDRESS_DATA_UPDATE) }
    router.post("/data")
      .handler { Address.parameterHandler.requireJson(it, "name", "type", "parentId") }
      .handler { handlerCreate(it, Data.ADDRESS_DATA_CREATE) }
    router.delete("/data/:id")
      .handler { handlerDelete(it, Data.ADDRESS_DATA_DELETE) }
    router.get("/data")
      .handler { Address.parameterHandler.requireParam(it, "type") }
      .handler { handlerPage(it, Data.ADDRESS_DATA_PAGE, this::dataPage) }
  }

  private fun dataParamId(context: RoutingContext): Long =
    context.request().getParam("id").toLong()

  private fun dataPage(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "type" to (context.request().getParam("type") ?: "0").toLong(),
      "name" to (context.request().getParam("name") ?: "")
    )

}
