plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath(kotlin("gradle-plugin", version = "1.3.72"))
    }
}

nexusPublishing {
    repositories {
        sonatype {
            packageGroup.set(properties["publishGroupId"].toString())
            username.set(properties["ossrhToken"].toString())
            password.set(properties["ossrhTokenPassword"].toString())
        }
    }
}
