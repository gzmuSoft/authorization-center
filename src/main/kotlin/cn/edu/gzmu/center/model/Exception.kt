package cn.edu.gzmu.center.model

/**
 * Exception.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/24 下午4:55
 */
class UnauthorizedException(message: String?) :
  Throwable("未经授权:${message ?: "用户未授权"}") {
  constructor() : this(null)
}

class ForbiddenException(message: String?) :
  Throwable("鉴权失败，${message ?: "您无权访问"}") {
  constructor() : this(null)
}

class DatabaseException(message: String?) :
  Throwable("服务器错误，${message ?: "数据异常"}") {
  constructor() : this(null)
}
