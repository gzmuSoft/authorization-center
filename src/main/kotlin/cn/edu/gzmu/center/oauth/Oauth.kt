package cn.edu.gzmu.center.oauth

import io.vertx.core.json.JsonObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * All keys that cannot be changed.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/23 下午3:48
 */
// config KEY
const val OAUTH = "oauth"
const val TOKEN = "token"
const val AUTHORIZATION = "authorization"
const val TOKEN_INFO = "token-info"
const val CLIENT_ID = "client-id"
const val CLIENT_SECRET = "client-secret"
const val REDIRECT_URI = "redirect-uri"
const val LOGOUT_URI = "logout-uri"
const val LOGOUT_REDIRECT_URL = "logout-redirect-url"
const val SCOPE = "scope"
const val SERVER = "server"
const val SECURITY = "security"

// request param
const val CODE = "code"

fun logoutUrl(config: JsonObject): String  {
  val url = config.getString(AUTHORIZATION) + config.getString(LOGOUT_URI)
  val param = "?redirect_url=${config.getString(LOGOUT_REDIRECT_URL)}" +
    if (config.getBoolean(SECURITY)) "&client_id=${config.getString(CLIENT_ID)}"
    else ""
  return url + URLEncoder.encode(param, StandardCharsets.UTF_8)
}
