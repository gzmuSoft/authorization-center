package cn.edu.gzmu.center.model

import com.google.common.base.CaseFormat
import com.google.common.base.Converter
import io.vertx.core.json.JsonObject
import kotlin.reflect.KProperty0


/**
 * Sql builder.
 * Must set [table].
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/9 下午10:56
 */
class Sql(private val table: String) {
  private var sql: String = ""
  private var index: Int = 1
  var converter: Converter<String, String> =
    CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE)

  private fun empty(): String = ""

  /**
   * Start with select sql.
   */
  fun select(condition: () -> String): Sql {
    clear()
    sql = "SELECT ${condition()} FROM $table "
    return this
  }

  /**
   * Start with update sql.
   */
  fun update(): Sql {
    clear()
    sql = "UPDATE $table "
    return this
  }

  /**
   * Set every field when update.
   */
  fun set(key: () -> String): Sql {
    condition("SET") { key() to "" }
    return this
  }

  /**
   * Set every field if not null when update.
   */
  fun setIf(property: KProperty0<*>): Sql {
    condition(",") { converter.convert(property.name)!! to property.get() }
    return this
  }

  /**
   * Where condition.
   */
  fun where(condition: () -> String = { empty() }): Sql {
    sql += "WHERE ${condition()} "
    return this
  }

  /**
   * Where condition.
   */
  fun where(condition: String): Sql {
    condition("WHERE") { condition to "" }
    return this
  }

  /**
   * Where condition if not null.
   */
  fun whereIf(field: () -> Pair<String, Any?>): Sql {
    condition("WHERE") { field() }
    return this
  }

  /**
   * Add [key] sql, if [field] second is null, skip.
   */
  private fun condition(key: String, field: () -> Pair<String, Any?> = { "name" to null }): Sql {
    val pair = field()
    if (pair.second !== null) {
      sql += "$key ${pair.first} = \$$index "
      index++
    }
    return this
  }

  /**
   * If pair second is null, skip.
   */
  fun and(field: () -> Pair<String, Any?> = { "name" to null }): Sql = condition("AND", field)

  /**
   * More condition, many [fields].
   */
  fun and(vararg fields: Pair<String, Any?>): Sql {
    fields.forEach { and { it } }
    return this
  }

  /**
   *  More condition, come from [json].
   */
  fun and(json: JsonObject): Sql {
    json.forEach { (key, value) ->
      and(key to value)
    }
    return this
  }


  /**
   * Come from property.
   */
  fun and(property: KProperty0<*>): Sql {
    this.and { converter.convert(property.name)!! to property.get() }
    return this
  }

  /**
   * Or condition.
   */
  fun or(field: () -> Pair<String, Any?> = { "name" to null }): Sql = condition("OR", field)

  /**
   * Or condition.
   */
  fun or(vararg fields: Pair<String, Any?>): Sql {
    fields.forEach { or { it } }
    return this
  }

  /**
   * Or condition.
   */
  fun or(json: JsonObject): Sql {
    json.forEach { (key, value) ->
      or(key to value)
    }
    return this
  }

  /**
   * or
   */
  fun or(property: KProperty0<*>): Sql {
    this.or { converter.convert(property.name)!! to property.get() }
    return this
  }

  /**
   * Restart sql.
   */
  fun clear(): Sql {
    sql = ""
    index = 1
    return this
  }

  /**
   * Get sql.
   */
  fun get(): String = sql
}

internal infix fun String.and(s: String): String = "$this, $s"
