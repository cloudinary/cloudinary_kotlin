plugins {
    base
    kotlin("jvm") version "1.3.72" apply false
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.1.4")
        classpath(kotlin("gradle-plugin", version = "1.3.72"))
    }
}


allprojects {

    group = "com.cloudinary.android"

    version = "1.0"

    repositories {
        google()
        jcenter()
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}

fun generateAndLoadLocalGradleProperties() {
    // Create default local gradle properties (ignored by git)
    val file = File("gradle-local.properties")
    if (file.createNewFile()) {
        file.writeText(
            "# Uncomment and fill in your cloudinary url from cloudinary.com/console.  *** IMPORTANT - This should be the url WITHOUT the api secret.\n# cloudinaryUrl=fill_in_your_cloudinary_url"
        )
    }

    loadExtraProperties(file)
}

fun loadExtraProperties(file: File) {
    val props = java.util.Properties()
    props.load(java.io.FileInputStream(file))

    props.forEach { key, value ->
        project.ext[key.toString()] = value
    }
}

generateAndLoadLocalGradleProperties()