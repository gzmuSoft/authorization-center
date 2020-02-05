package cn.edu.gzmu.center.verticle

import cn.edu.gzmu.center.model.extension.CONFIG_ADDRESS
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/2 下午11:30
 */
class ConfigVerticle : CoroutineVerticle() {
  private val log: Logger = LoggerFactory.getLogger(ConfigVerticle::class.java.name)

  override suspend fun start() {
    val store =
      ConfigStoreOptions()
        .setType("file")
        .setFormat("yaml")
        .setConfig(JsonObject().put("path", "application.yml"))
    val retrieverOptions = ConfigRetrieverOptions()
      .setScanPeriod(2000)
      .addStore(store)
    val config = ConfigRetriever.create(vertx, retrieverOptions).getConfigAwait()
    val eventBus = vertx.eventBus()
    val consumer: MessageConsumer<String> = eventBus.consumer(CONFIG_ADDRESS)
    consumer.handler { it.reply(config.getJsonObject(it.body())) }
    log.info("Success start config verticle......")
  }
}
