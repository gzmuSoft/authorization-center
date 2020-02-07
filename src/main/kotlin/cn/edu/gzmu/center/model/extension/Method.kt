package cn.edu.gzmu.center.model.extension

import cn.edu.gzmu.center.model.DatabaseException
import cn.edu.gzmu.center.model.extension.Address.Companion.DATABASE
import cn.edu.gzmu.center.oauth.Oauth.Companion.OAUTH
import com.google.common.base.CaseFormat
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.AsyncResult
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.get
import io.vertx.sqlclient.Row
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.full.primaryConstructor

fun JsonObject.oauth(key: String): String =
  this.getJsonObject(OAUTH).getString(key)

fun JsonObject.database(key: String, default: String = ""): String =
  this.getJsonObject(DATABASE).getString(key, default)

inline fun <reified To : Any> JsonArray.toTypeArray(): Array<To> =
  Array(this.size()) { this.get<To>(it) }

/**
 * In default, we want to mapper result by one to one field.
 * But do not have a method to do this. So, we need to create a method to map.
 */
inline fun <reified To : Any> Row.toJsonObject(): JsonObject =
  JsonObject.mapFrom(To::class.primaryConstructor!!.parameters
    .map { parameter ->
      val nameLine = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, parameter.name!!)
      var value = (this.getValue(parameter.name)
        ?: this.getValue(nameLine))
      value = when (value) {
        is LocalDateTime -> localDateTimeConversion(value)
        is LocalDate -> localDateConversion(value)
        else -> value
      }
      parameter.name to value
    }
    .toMap())

object KotlinJson {
  private val json = Json(JsonConfiguration.Stable)
  fun json() = json
}

fun <T> handleResult(context: RoutingContext, ar: AsyncResult<Message<T>>) {
  if (ar.failed()) context.fail(DatabaseException(ar.cause().localizedMessage))
  context.response()
    .setStatusCode(HttpResponseStatus.OK.code())
    .end(ar.result().body().toString())
}
