plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    signing
    `maven-publish`
    id("de.marcphilipp.nexus-publish")
}

repositories {
    google()
    mavenCentral()
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}

nexusPublishing {
    repositories {
        sonatype {
            packageGroup.set(properties["publishGroupId"].toString())
            username.set(properties["ossrhUsername"].toString())
            password.set(properties["ossrhPassword"].toString())
        }
    }
}