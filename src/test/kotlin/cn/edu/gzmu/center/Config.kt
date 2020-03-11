package cn.edu.gzmu.center

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * Test config.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/7 上午11:33
 */
object Config {
  val database: JsonObject = jsonObjectOf(
    "port" to 5432,
    "host" to "127.0.0.1",
    "database" to "gzmu",
    "user" to "postgres",
    "password" to "123456",
    "maxSize" to 20,
    // Will use test schema.
    "schema" to "test"
  )
  val web: JsonObject = jsonObjectOf(
    "name" to "auth-center-web-test",
    "server" to jsonObjectOf("port" to 9000),
    "oauth" to jsonObjectOf(
      "server" to "http://118.24.1.170:8888",
      "token" to "/oauth/token",
      "authorization" to "/oauth/authorize",
      "authorization-form" to "/authorization/form",
      "token-info" to "/oauth/check_token",
      "client-id" to "gzmu-auth",
      "client-secret" to "gzmu-auth-secret",
      "redirect-uri" to "http://127.0.0.1:8081/#/login",
      "scope" to "READ",
      "logout-uri" to "/oauth/logout",
      "logout-redirect-url" to "http://127.0.0.1:8081/#/logout",
      "security" to false
    )
  )
}
