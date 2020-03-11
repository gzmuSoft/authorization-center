package cn.edu.gzmu.center

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.*
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/3/11 下午6:43
 */
fun main() {
  Vertx.vertx().deployVerticle(BootVerticle())
}

private class BootVerticle : AbstractVerticle() {
  companion object {
    private const val CONF_KEY = "configuration"
    private const val INSTANCES_KEY = "instances"
    private const val HA_KEY = "high-availability"
    private const val MAX_WORKER_EXEC_TIME_KEY = "max-worker-execution-time"
    private const val WORKER_KEY = "worker"
    private const val WORKER_POOL_NAME_KEY = "worker-pool-name"
    private const val WORKER_POOL_SIZE_KEY = "worker-pool-size"
  }

  override fun start(startPromise: Promise<Void>?) {
    val configStoreOptions = ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(jsonObjectOf("path" to "application.yaml"))
    val retriever = ConfigRetriever.create(
      vertx,
      ConfigRetrieverOptions().addStore(configStoreOptions)
    )
    retriever.getConfig {
      if (it.failed()) {
        startPromise?.fail(it.cause())
        throw it.cause()
      }
      val verticles = it.result()
      verticles.fieldNames()
        .map { key -> deployVerticle(verticles.getJsonObject(key)) }
        .reduce { t: Future<String>, u: Future<String> ->
          t.compose { u }
        }
        .onSuccess { startPromise?.complete() }
        .onFailure { fail -> startPromise?.fail(fail.cause) }
    }
  }

  override fun stop(stopFuture: Promise<Void>?) {
    vertx.deploymentIDs().forEach { vertx.undeploy(it) }
  }

  private fun deployVerticle(config: JsonObject): Future<String> {
    val promise = Promise.promise<String>()
    val name = config.getString("name")
    try {
      val options = DeploymentOptions()
        .setInstances(config.getInteger(INSTANCES_KEY, 1))
        .setConfig(config.getJsonObject(CONF_KEY, JsonObject()))
        .setHa(config.getBoolean(HA_KEY, false))
        .setMaxWorkerExecuteTime(config.getLong(MAX_WORKER_EXEC_TIME_KEY, Long.MAX_VALUE))
        .setWorker(config.getBoolean(WORKER_KEY, false))
        .setWorkerPoolName(config.getString(WORKER_POOL_NAME_KEY, null))
        .setWorkerPoolSize(config.getInteger(WORKER_POOL_SIZE_KEY, 1))
      vertx.deployVerticle(name, options)
        .onSuccess { promise.complete(it) }
        .onFailure { promise.fail(it) }
    } catch (e: Exception) {
      promise.fail(e)
    }
    return promise.future()
  }

}
