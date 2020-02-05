package cn.edu.gzmu.center.model

import cn.edu.gzmu.center.oauth.OAUTH
import io.vertx.core.json.JsonObject

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/1 下午9:04
 */
const val DATABASE = "database"
fun JsonObject.oauth(key: String): String = this.getJsonObject(OAUTH).getString(key)
fun JsonObject.database(key: String, default: String = ""): String = this.getJsonObject(DATABASE).getString(key, default)
fun JsonObject.databaseInt(key: String, default: Int = 5432): Int = this.getJsonObject(DATABASE).getInteger(key, default)
