package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.DatabaseException
import cn.edu.gzmu.center.model.address.DASHBOARD_DATE_INFO
import cn.edu.gzmu.center.model.address.DASHBOARD_INFO
import cn.edu.gzmu.center.model.address.GET_API_INFO
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.CompositeFuture
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/5/19 下午9:25
 */
class DashboardHandler(router: Router, private val eventBus: EventBus) : BaseHandler(eventBus) {
  companion object {
    const val RESOURCE = "/dashboard"
  }

  init {
    router.get(RESOURCE).handler(::dashboard)
    router.get("$RESOURCE/date").handler(::date)
  }

  private fun dashboard(context: RoutingContext) {
    val username: String = context.get("username")
    CompositeFuture.all(
      eventBus.request<JsonObject>(GET_API_INFO, username),
      eventBus.request<JsonObject>(DASHBOARD_INFO, null)
    ).onComplete {
      if (it.succeeded()) {
        val future = it.result()
        val apiInfo = future.resultAt<Message<JsonObject>>(0).body()
        val dashboard = future.resultAt<Message<JsonObject>>(1).body()
        context.response()
          .setStatusCode(HttpResponseStatus.OK.code())
          .end(apiInfo.mergeIn(dashboard).toBuffer())
      } else {
        context.fail(DatabaseException(it.cause().localizedMessage))
      }
    }
  }

  private fun date(context: RoutingContext) {
    eventBus.request<JsonObject>(DASHBOARD_DATE_INFO, null) {
      if (it.succeeded()) {
        val result = it.result()
        context.response()
          .setStatusCode(HttpResponseStatus.OK.code())
          .end(result.body().toBuffer())
      } else {
        context.fail(DatabaseException(it.cause().localizedMessage))
      }
    }
  }
}
