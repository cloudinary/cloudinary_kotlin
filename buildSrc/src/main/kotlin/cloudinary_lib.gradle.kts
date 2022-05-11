plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    signing
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}