package cn.edu.gzmu.center.model

import io.vertx.ext.web.RoutingContext
import java.util.*

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/8 下午9:38
 */
class ParameterHandler {

  fun requireJson(context: RoutingContext, vararg keys: String) {
    val body = context.bodyAsJson
    for (key in keys) {
      if (Objects.isNull(body.getValue(key))) {
        context.fail(BadRequestException())
        return
      }
    }
    context.next()
  }

  fun requireParam(context: RoutingContext, vararg keys: String) {
    val params = context.request().params()
    for (key in keys) {
      if (Objects.isNull(params[key])) {
        context.fail(BadRequestException())
        return
      }
    }
    context.next()
  }

  fun equalsJson(context: RoutingContext, error: String? = null, pair: Pair<String, String>) {
    val body = context.bodyAsJson
    if (!Objects.equals(body.getValue(pair.first), body.getValue(pair.second))) {
      context.fail(BadRequestException(error))
      return
    }
    context.next()
  }
}
