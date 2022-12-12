val publicationName = "kotlin-url-gen"

plugins {
    id("cloudinary_lib")
}

dependencies {
    api("com.cloudinary:kotlin-transformation-builder-sdk:1.+")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(project.the<SourceSetContainer>()["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    val s = "$buildDir/dokka/$publicationName"
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
                    appendNode("url", properties["githubUrl"])
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