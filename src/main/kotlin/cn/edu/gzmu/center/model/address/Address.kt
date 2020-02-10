package cn.edu.gzmu.center.model.address

import cn.edu.gzmu.center.util.ParameterHandler

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/2 下午11:35
 */
internal class Address {
  companion object {
    val parameterHandler = ParameterHandler()
    const val CONFIG_ADDRESS = "authorization-center-config"
    const val DATABASE = "database"
    const val RESULT = "result"
    const val LOG_ROUNDS = 12
  }
}
