plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven(url="https://jitpack.io")
}

application {
    mainClassName = "MainKt"
}

dependencies {
    implementation("com.beust", "klaxon", "4.0.2")
    implementation("commons-codec:commons-codec:1.14")
    implementation(kotlin("stdlib-jdk8"))
    implementation("commons-io:commons-io:2.6")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
