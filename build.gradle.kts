import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  kotlin("jvm") version "1.3.61"
  kotlin("plugin.serialization") version "1.3.61"
  id("io.vertx.vertx-plugin") version "1.0.1"
  id("org.jetbrains.dokka") version "0.10.0"
}

group = "cn.edu.gzmu.center"
version = "1.0.0-SNAPSHOT"

repositories {
  maven("http://maven.aliyun.com/nexus/content/groups/public/")
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  mavenCentral()
  jcenter()
}

val mainVerticleName = "cn.edu.gzmu.center.MainVerticle"
val kotlinVersion by extra { "1.3.61" }
val log4j2Version by extra { "2.13.0" }
val vertxBootVersion by extra { "1.1.2" }
val guavaVersion by extra { "28.2-jre" }
val jbcryptVersion by extra { "0.4" }

dependencies {
  implementation(kotlin("reflect"))
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
  implementation("org.mindrot:jbcrypt:$jbcryptVersion")
  implementation("io.vertx:vertx-web-client")
  implementation("io.vertx:vertx-auth-jwt")
  implementation("io.vertx:vertx-auth-oauth2")
  implementation("io.vertx:vertx-unit")
  implementation("io.vertx:vertx-web")
  implementation("io.vertx:vertx-config")
  implementation("io.vertx:vertx-config-yaml")
  implementation("io.vertx:vertx-pg-client")
  implementation("io.vertx:vertx-lang-kotlin-coroutines")
  implementation("io.vertx:vertx-consul-client")
  implementation("io.vertx:vertx-lang-kotlin")
  implementation("io.vertx:vertx-consul-client")
  implementation("io.github.jponge:vertx-boot:$vertxBootVersion")
  implementation("org.apache.logging.log4j:log4j-slf4j18-impl:$log4j2Version")
  implementation("com.google.guava:guava:$guavaVersion")
  testImplementation("io.vertx:vertx-junit5")
}

tasks {
  "dokka"(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "docs/kotlin"
    configuration {
      includes = listOf("src/main/resources/packages.md")
    }
  }
  compileKotlin {
    kotlinOptions.jvmTarget = "11"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "11"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = mutableSetOf(PASSED, FAILED, SKIPPED)
  }
}

vertx {
  mainVerticle = "io.github.jponge.vertx.boot.BootVerticle"
  vertxVersion = "4.0.0-SNAPSHOT"
}
