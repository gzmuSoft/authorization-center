package cn.edu.gzmu.center.model.extension

import cn.edu.gzmu.center.model.extension.Address.Companion.DATABASE
import cn.edu.gzmu.center.oauth.Oauth.Companion.OAUTH
import com.google.common.base.CaseFormat
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get
import io.vertx.sqlclient.Row
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.reflect.full.primaryConstructor


/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/1 下午9:04
 */

fun JsonObject.oauth(key: String): String =
  this.getJsonObject(OAUTH).getString(key)

fun JsonObject.database(key: String, default: String = ""): String =
  this.getJsonObject(DATABASE).getString(key, default)

fun JsonObject.databaseInt(key: String, default: Int = 5432): Int =
  this.getJsonObject(DATABASE).getInteger(key, default)

inline fun <reified To : Any> JsonArray.toTypeArray(): Array<To> =
  Array(this.size()) { this.get<To>(it) }

/**
 * In default, we want to mapper result by one to one field.
 * But do not have a method to do this. So, we need to create a method to map.
 */
inline fun <reified To : Any> Row.mapAs(): To =
  To::class.primaryConstructor!!.let {
    it.parameters
      .map { parameter ->
        val nameLine = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, parameter.name!!)
        parameter to (this.getValue(it.name)
          ?: this.getValue(nameLine))
      }
      .toMap()
      .let(it::callBy)
  }

object KotlinJson {
  private val json = Json(JsonConfiguration.Stable)
  fun json() = json
}


