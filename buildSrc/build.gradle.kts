plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.10.0")
    implementation("de.marcphilipp.gradle:nexus-publish-plugin:0.4.0")
}
