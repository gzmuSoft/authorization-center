package cn.edu.gzmu.center.model

import com.google.common.base.CaseFormat
import com.google.common.base.Converter
import io.vertx.core.json.JsonObject
import kotlin.reflect.KClass
import kotlin.reflect.KProperty0
import kotlin.reflect.full.memberProperties


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
   * Select by KClass [condition].
   */
  fun select(condition: KClass<*>, vararg exclude: String = emptyArray()): Sql {
    select {
      condition.memberProperties.map {
        converter.convert(it.name).toString()
      }.filterNot { exclude.contains(it) }.joinToString(", ")
    }
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
   * Insert into
   */
  fun insert(vararg fields: String): Sql {
    val fieldNames = fields.joinToString(",")
    val values = fields.joinToString(",") { "$${index++}" }
    sql = "INSERT INTO $table ($fieldNames) VALUES (${values})"
    return this
  }

  fun insert(vararg properties: KProperty0<*>): Sql {
    val fields = properties.map { converter.convert(it.name) }
    val fieldNames = fields.joinToString(",")
    val values = fields.joinToString(",") { "$${index++}" }
    sql = "INSERT INTO $table ($fieldNames) VALUES (${values})"
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
   * Add is enable
   */
  fun whereEnable(): Sql {
    sql += "WHERE is_enable = true "
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
      sql += "$key ${converter.convert(pair.first)} = \$$index "
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
   * more condition, not blank and not null
   */
  fun andNotBlank(vararg fields: Pair<String, Any?>): Sql {
    fields.filter { it.second?.toString()?.isNotBlank() == true }
      .forEach { and { it } }
    return this
  }

  /**
   * more condition, not blank and not null
   */
  fun andNotBlank(filed: () -> Pair<String, Any?>): Sql {
    val pair = filed()
    if (pair.second?.toString()?.isNotBlank() == true) {
      and { pair }
    }
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

  fun and(where: String): Sql {
    sql += "AND $where "
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
   * order by [field]
   */
  fun orderBy(field: () -> String): Sql {
    sql += "ORDER BY ${field()} "
    return this
  }

  /**
   * left join
   */
  fun leftJoin(field: () -> String): Sql {
    sql += "LEFT JOIN ${field()} "
    return this
  }

  /**
   * on
   */
  fun on(field: () -> String): Sql {
    sql += "ON ${field()} "
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

  fun count(field: () -> String = { "id" }): String {
    val start = sql.indexOf("SELECT") + 7
    val end = sql.indexOf("FROM") - 1
    return sql.replaceRange(IntRange(start, end), "count(${field()}) ")
  }

  /**
   * page
   */
  fun page(sort: String = "sort"): Sql {
    if (sort.isNotBlank()) {
      sql += "ORDER BY $sort "
    }
    sql += "LIMIT $${index++} OFFSET $${index++} "
    return this
  }

  fun like(name: () -> String): Sql {
    sql += "AND ${name()} LIKE $${index++} "
    return this
  }

}

internal infix fun String.and(s: String): String = "$this, $s"
