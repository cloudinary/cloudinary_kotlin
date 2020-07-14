import java.util.*

val publicationName by extra("kotlin-url-gen")
plugins {
    kotlin("jvm")
    signing
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "0.10.1"
}

dependencies {
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
    from("$buildDir/dokka/url-gen")
}

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = properties["publishGroupId"].toString()
            artifactId = publicationName
            version = properties["publishVersion"].toString()
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
                "${publicationName}-${properties["publishVersion"]}-javadoc.jar.asc"
            )
            rename(
                "${project.name}-sources.jar.asc",
                "${publicationName}-${properties["publishVersion"]}-sources.jar.asc"
            )
            rename(
                "${project.name}.jar.asc",
                "${publicationName}-${properties["publishVersion"]}.jar.asc"
            )

        }

        from("$buildDir/publications/$publicationName") {
            include("pom-default.xml.asc")
            rename("pom-default.xml.asc", "${publicationName}-${properties["publishVersion"]}.pom.asc")
        }

        into("com/cloudinary/${publicationName}/${properties["publishVersion"]}")

    })

    pkg.apply {
        repo = "cloudinary"
        name = publicationName
        userOrg = "cloudinary"
        setLicenses("MIT")
        websiteUrl = "https://cloudinary.com"
        githubRepo = "https://github.com/cloudinary/cloudinary_kotlin"
        version.apply {
            name = findProperty("publishVersion").toString()
            desc = properties["publishDescription"].toString()
            released = Date().toString()
            vcsTag = properties["publishVersion"].toString()
        }
    }
}