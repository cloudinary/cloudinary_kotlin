val publicationName = "kotlin-uploader"

plugins {
    id("cloudinary_lib")
    kotlin("kapt")
}

dependencies {
    implementation(files("libs/transformation-builder-1.0.0.jar"))
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

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(project.the<SourceSetContainer>()["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    val s = "$buildDir/dokka/${publicationName}"
    println(s)
    from(s)
}

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = properties["publishGroupId"].toString()
            artifactId = publicationName
            version = properties["version"].toString()
            from(components["java"])
            artifact(sourcesJar)
            artifact(javadocJar)
            pom.withXml {
                asNode().apply {
                    appendNode("description", properties["publishDescription"])
                    appendNode("name", publicationName)
                    appendNode("url", properties["githubUrl"].toString())
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", properties["licenseName"])
                        appendNode("url", properties["licenseUrl"])
                        appendNode("distribution", "repo")
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", properties["developerId"])
                        appendNode("name", properties["developerName"])
                    }
                    appendNode("scm").apply {
                        appendNode("url", properties["scmUrl"])
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications[publicationName])
}