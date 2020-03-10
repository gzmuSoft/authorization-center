package cn.edu.gzmu.center

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import io.vertx.core.*
import io.vertx.core.json.JsonObject
import java.util.stream.Collectors

fun main() {
  Vertx.vertx().deployVerticle(BootVerticle()) {
    if (it.failed()) throw it.cause()
  }
}

class BootVerticle : AbstractVerticle() {
  companion object {
    private const val VERTX_BOOT_VERTICLES_PATH = "vertx-boot.verticles"
    private const val CONF_KEY = "configuration"
    private const val INSTANCES_KEY = "instances"
    private const val HA_KEY = "high-availability"
    private const val MAX_WORKER_EXEC_TIME_KEY = "max-worker-execution-time"
    private const val WORKER_KEY = "worker"
    private const val WORKER_POOL_NAME_KEY = "worker-pool-name"
    private const val WORKER_POOL_SIZE_KEY = "worker-pool-size"
  }

  override fun start(promise: Promise<Void>) {
    try {
      val bootConfig = ConfigFactory.load()
      val configList =
        bootConfig.getConfig(VERTX_BOOT_VERTICLES_PATH).root().keys.stream()
          .map { key: String ->
            bootConfig.getConfig("$VERTX_BOOT_VERTICLES_PATH.$key")
          }.collect(
            Collectors.toList()
          )
      val future = Future.future<String> { }
      configList.stream()
        .map(::deployVerticle)
        .reduce { t: Future<String>, u: Future<String> ->
          t.compose { u }
        }.orElse(future)
        .onSuccess { promise.complete() }
        .onFailure { promise.fail(it.cause) }

    } catch (t: Throwable) {
      promise.fail(t)
    }
  }

  private fun deployVerticle(config: Config): Future<String> {
    val promise = Promise.promise<String>()
    try {
      val name = config.getString("name")
      val options = DeploymentOptions()
        .setInstances(getInstances(config))
        .setConfig(getConfig(config))
        .setHa(getHa(config))
        .setMaxWorkerExecuteTime(getMaxWorkerExecuteTime(config))
        .setWorker(getWorker(config))
        .setWorkerPoolName(getWorkerPoolName(config))
        .setWorkerPoolSize(getWorkerPoolSize(config))
      vertx.deployVerticle(name, options) {
        if (it.succeeded()) promise.complete(it.result())
        else promise.fail(it.cause())
      }
    } catch (t: Throwable) {
      promise.fail(t)
    }
    return promise.future()
  }

  private fun getWorkerPoolSize(config: Config): Int =
    if (config.hasPath(WORKER_POOL_SIZE_KEY))
      config.getInt(WORKER_POOL_SIZE_KEY)
    else 1


  private fun getWorkerPoolName(config: Config): String? =
    if (config.hasPath(WORKER_POOL_NAME_KEY))
      config.getString(WORKER_POOL_NAME_KEY)
    else null

  private fun getWorker(config: Config): Boolean =
    if (config.hasPath(WORKER_KEY))
      config.getBoolean(WORKER_KEY)
    else false


  private fun getMaxWorkerExecuteTime(config: Config): Long =
    if (config.hasPath(MAX_WORKER_EXEC_TIME_KEY))
      config.getLong(MAX_WORKER_EXEC_TIME_KEY)
    else Long.MAX_VALUE

  private fun getHa(config: Config): Boolean =
    if (config.hasPath(HA_KEY))
      config.getBoolean(HA_KEY)
    else false


  private fun getConfig(config: Config): JsonObject? =
    if (config.hasPath(CONF_KEY))
      JsonObject(
        config.getValue(CONF_KEY).render(
          ConfigRenderOptions.concise()
        )
      )
    else JsonObject()

  private fun getInstances(config: Config): Int =
    if (config.hasPath(INSTANCES_KEY))
      config.getInt(INSTANCES_KEY)
    else 1
}
