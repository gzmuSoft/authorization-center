package cn.edu.gzmu.center

import io.github.jponge.vertx.boot.BootVerticle
import io.vertx.core.Vertx

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/1 下午4:25
 */
fun main() {
  Vertx.vertx().deployVerticle(BootVerticle::class.java.name)
}
