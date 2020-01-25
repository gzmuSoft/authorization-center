package cn.edu.gzmu.center.model

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/24 下午4:55
 */
class UnauthorizedException(message: String?) : Throwable("未经授权:${message}") {
  constructor() : this("未经授权:用户未授权")
}

class ForbiddenException : Throwable("鉴权失败，您无权访问")
