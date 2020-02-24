package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.entity.Client
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/23 下午8:06
 */
class ClientHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  companion object {
    const val RESOURCE = "/client"
  }

  init {
    router.get(RESOURCE)
      .handler { handlerGet<JsonArray>(it, Client.ADDRESS_CLIENT) }
    router.post(RESOURCE)
      .handler {
        Address.parameterHandler.requireJson(
          it, "id", "name", "clientId", "resourceIds", "clientSecret", "scope",
          "grantTypes", "accessTokenValidity", "refreshTokenValidity", "remark"
        )
      }
      .handler { handlerCreate(it, Client.ADDRESS_CLIENT_POST) }
  }
}
