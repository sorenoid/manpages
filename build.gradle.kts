val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.20"
    id("io.ktor.plugin") version "2.1.3"
    id("com.google.cloud.tools.appengine") version "2.4.3"

}

group = "com.bandor"
version = "0.0.1"
application {
    mainClass.set("com.bandor.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

appengine {
    stage {
        setArtifact(File("build/libs/com.bandor.manpages-all.jar"))
//        artifact = File("build/libs/com.bandor.manpages-all.jar")
    }

    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "GCLOUD_CONFIG"
        stopPreviousVersion = true
        promote = true
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-freemarker-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.google.appengine:appengine:+")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}