plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    api(project(":url-gen"))
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")

    compileOnly("org.apache.httpcomponents:httpclient:4.5.6")
    compileOnly("org.apache.httpcomponents:httpmime:4.5.6")
    compileOnly("com.squareup.okhttp3:okhttp:3.11.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")

    testImplementation("org.apache.httpcomponents:httpclient:4.5.6")
    testImplementation("org.apache.httpcomponents:httpmime:4.5.6")
    testImplementation("com.squareup.okhttp3:okhttp:3.11.0")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))

}