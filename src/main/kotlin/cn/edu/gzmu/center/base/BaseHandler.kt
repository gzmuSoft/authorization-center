package cn.edu.gzmu.center.base

import cn.edu.gzmu.center.model.DatabaseException
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.AsyncResult
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

/**
 * Base Handler.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/10 下午8:30
 */
open class BaseHandler(private val eventBus: EventBus) {
  init {
    // In init, init all ordinary routing
  }

  open suspend fun route() {
    // In suspend function, init all Coroutine routing
  }

  fun handlerCreate(context: RoutingContext, address: String) {
    handlerJson(context, address, HttpResponseStatus.CREATED)
  }

  fun handlerPatch(context: RoutingContext, address: String) {
    handlerJson(context, address, HttpResponseStatus.NO_CONTENT)
  }

  fun handlerPage(
    context: RoutingContext,
    address: String,
    params: (context: RoutingContext) -> JsonObject = { JsonObject() }
  ) {
    val message = params(context)
    // Add page params.
    message.put("sort", context.request().getParam("sort") ?: "")
    val page = context.request().getParam("page").toLong()
    val size = context.request().getParam("size").toLong()
    message.put("offset", (page - 1) * size)
    message.put("size", size)
    eventBus.request<JsonObject>(address, message) {
      handleResult<JsonObject>(context, it)
    }
  }

  fun <T, R> handlerGet(
    context: RoutingContext, address: String,
    params: (context: RoutingContext) -> T
  ) {
    eventBus.request<R>(address, params(context)) {
      handleResult<R>(context, it)
    }
  }

  private fun handlerJson(context: RoutingContext, address: String, status: HttpResponseStatus) {
    val user = context.bodyAsJson
    user.put("userId", context.get<Long>("id"))
    user.put("username", context.get<String>("username"))
    user.put("modifyUser", context.get<String>("username"))
    user.put("student", context.user().principal().getBoolean("is_student"))
    user.put("teacher", context.user().principal().getBoolean("is_teacher"))
    eventBus.request<Unit>(address, user) {
      handleNoResult(context, status, it)
    }
  }

  private fun <T> handleResult(context: RoutingContext, ar: AsyncResult<Message<T>>) {
    if (ar.failed()) {
      context.fail(DatabaseException(ar.cause().localizedMessage))
      return
    }
    context.response()
      .setStatusCode(HttpResponseStatus.OK.code())
      .end(ar.result().body().toString())
  }

  private fun handleNoResult(
    context: RoutingContext, status: HttpResponseStatus = HttpResponseStatus.OK,
    ar: AsyncResult<Message<Unit>>
  ) {
    if (ar.failed()) {
      context.fail(DatabaseException(ar.cause().localizedMessage))
      return
    }
    context.response()
      .setStatusCode(status.code())
      .end()
  }

}
