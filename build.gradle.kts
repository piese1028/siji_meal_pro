/*
 * @author Bruce0203, apwlq
 * @license MIT License
 */

val kotlin_version = "1.9.0"
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}
plugins {
    kotlin("jvm") version "1.9.0"
}

apply(plugin = "com.github.johnrengelman.shadow")
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${rootProject.name}.jar")
}

tasks.withType<Jar> {
    manifest {
        attributes(mapOf(
            "Main-Class" to "io.github.apwlq.schoolmealinfo.MainKt"
        ))
    }
}

group = "io.github.apwlq"
version = "1.1-bsdg"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    jcenter()
}

dependencies {
    api("io.github.leeseojune53:neis-api:1.0.3")
    api("org.slf4j:slf4j-api:1.7.36")
    api("org.slf4j:slf4j-simple:1.7.36")
    implementation("com.github.instagram4j:instagram4j:2.0.7")
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    api("de.taimos:totp:1.0")
    api("commons-codec:commons-codec:1.10")
    api("com.google.zxing:javase:3.2.1")
}

sourceSets.getByName("main") {
    resources.srcDir("assets")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}
