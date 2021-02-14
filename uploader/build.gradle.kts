import java.util.*


val publicationName by extra("kotlin-uploader")

plugins {
    kotlin("jvm")
    kotlin("kapt")
    signing
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "0.10.1"
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(project.the<SourceSetContainer>()["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/url-gen")
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

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = properties["publishGroupId"].toString()
            artifactId = publicationName
            version = properties["publishUploaderVersion"].toString()
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

bintray {
    user = findProperty("bintray.user").toString()
    key = findProperty("bintray.key").toString()
    setPublications(publicationName)

    filesSpec(closureOf<com.jfrog.bintray.gradle.tasks.RecordingCopyTask> {

        from("$buildDir/libs") {
            include("*.jar.asc")
            rename(
                "${project.name}-javadoc.jar.asc",
                "${publicationName}-${properties["publishUploaderVersion"]}-javadoc.jar.asc"
            )
            rename(
                "${project.name}-sources.jar.asc",
                "${publicationName}-${properties["publishUploaderVersion"]}-sources.jar.asc"
            )
            rename(
                "${project.name}.jar.asc",
                "${publicationName}-${properties["publishUploaderVersion"]}.jar.asc"
            )

        }

        from("$buildDir/publications/$publicationName") {
            include("pom-default.xml.asc")
            rename("pom-default.xml.asc", "${publicationName}-${properties["publishUploaderVersion"]}.pom.asc")
        }

        into("com/cloudinary/${publicationName}/${properties["publishUploaderVersion"]}")

    })

    pkg.apply {
        repo = "cloudinary"
        name = publicationName
        userOrg = "cloudinary"
        setLicenses("MIT")
        websiteUrl = "https://cloudinary.com"
        githubRepo = "https://github.com/cloudinary/cloudinary_kotlin"
        version.apply {
            name = findProperty("publishUploaderVersion").toString()
            desc = properties["publishDescription"].toString()
            released = Date().toString()
            vcsTag = properties["publishUploaderVersion"].toString()
        }
    }
}