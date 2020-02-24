package cn.edu.gzmu.center.model.extension

import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.address.Address.Companion.DATABASE
import cn.edu.gzmu.center.model.address.Oauth.Companion.OAUTH
import com.google.common.base.CaseFormat
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.full.primaryConstructor

fun JsonObject.oauth(key: String): String =
  this.getJsonObject(OAUTH).getString(key)

fun JsonObject.database(key: String, default: String = ""): String =
  this.getJsonObject(DATABASE).getString(key, default)

fun <T> JsonObject.mapAs(deserializer: DeserializationStrategy<T>): T =
  KotlinJson.json().parse(deserializer, this.toString())

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

/**
 * Add optional param.
 */
internal fun Tuple.addOptional(value: Any?): Tuple =
  if (value === null) this
  else this.addValue(value)

internal fun Tuple.addOptional(vararg values: Any?): Tuple {
  values.filterNotNull().forEach { this.addValue(it) }
  return this
}

/**
 * Add optional param.
 */
internal fun Tuple.addOrNull(value: Any?): Tuple =
  if (value === null || value.toString().trim().isBlank()) this.addValue(null)
  else this.addValue(value)

/**
 * Get Singleton kotnlin json.
 */
object KotlinJson {

  private val json = Json(JsonConfiguration(strictMode = false))

  fun json() = json

}

/**
 * Encode password.
 * if [password] length less then 6, return 000000
 */
fun encodePassword(password: String): String =
  if (password.length < 6) BCrypt.hashpw("000000", BCrypt.gensalt(Address.LOG_ROUNDS))
  else BCrypt.hashpw(password.substring(password.length - 6), BCrypt.gensalt(Address.LOG_ROUNDS))
