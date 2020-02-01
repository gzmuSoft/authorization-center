import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  application
  kotlin("jvm") version "1.3.20"
  id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "cn.edu.gzmu.center"
version = "1.0.0-SNAPSHOT"

repositories {
  maven("http://maven.aliyun.com/nexus/content/groups/public/")
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  mavenCentral()
  jcenter()
}


application {
  mainClassName = "io.vertx.core.Launcher"
}
val mainVerticleName = "cn.edu.gzmu.center.MainVerticle"

val kotlinVersion by extra { "1.3.20" }
val vertxVersion by extra { "4.0.0-SNAPSHOT" }
val log4j2Version by extra { "2.13.0" }
val junitJupiterEngineVersion by extra { "5.4.0" }

dependencies {
  implementation("io.vertx:vertx-web-client:$vertxVersion")
  implementation("io.vertx:vertx-auth-jwt:$vertxVersion")
  implementation("io.vertx:vertx-auth-oauth2:$vertxVersion")
  implementation("io.vertx:vertx-unit:$vertxVersion")
  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-config:$vertxVersion")
  implementation("io.vertx:vertx-config-yaml:$vertxVersion")
  implementation("io.vertx:vertx-pg-client:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
  implementation("io.vertx:vertx-consul-client:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
  implementation("org.apache.logging.log4j:log4j-slf4j18-impl:$log4j2Version")

  testImplementation("io.vertx:vertx-junit5:$vertxVersion")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterEngineVersion")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterEngineVersion")
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks

compileTestKotlin.kotlinOptions {
  jvmTarget = "1.8"
}


tasks.shadowJar {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to mainVerticleName ))
  }
  mergeServiceFiles {
    include("META-INF/services/io.vertx.core.spi.VerticleFactory")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = mutableSetOf(PASSED, FAILED, SKIPPED)
  }
}

tasks {
  val watchForChange = "src/**/*"
  val doOnChange = "./gradlew classes"
  "run"(JavaExec::class) {
    args("run", mainVerticleName,
      "--redeploy=$watchForChange",
      "--launcher-class=${application.mainClassName}",
      "--on-redeploy=$doOnChange")
  }
}

tasks {
  build {
    dependsOn(shadowJar)
  }
}
