package cn.edu.gzmu.center.model.address

/**
 * All keys that cannot be changed.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/23 下午3:48
 */
internal class Oauth {
  companion object {
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
    const val ADDRESS_ROLE_RESOURCE = "address_role_resource"
    const val ADDRESS_ME = "address_me"

    // request param
    const val CODE = "code"

  }
}

