import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "com.cloudinary"
version = "1.0"


dependencies {
    api(project(":url-gen"))
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")

    compileOnly("org.apache.httpcomponents:httpclient:4.5.5")
    compileOnly("org.apache.httpcomponents:httpmime:4.5.5")
    compileOnly("com.squareup.okhttp3:okhttp:3.11.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")

    testCompile("org.apache.httpcomponents:httpclient:4.5.5")
    testCompile("org.apache.httpcomponents:httpmime:4.5.5")
    testCompile("com.squareup.okhttp3:okhttp:3.11.0")
    testCompile("junit", "junit", "4.12")
    testCompile(kotlin("test"))
    testCompile(kotlin("test-junit"))

    implementation(kotlin("stdlib-jdk7"))
    testImplementation("junit:junit:4.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
