import java.io.FileInputStream
import java.util.*

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
    repositories {
        google()
        jcenter()
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
    val props = Properties()
    props.load(FileInputStream(file))

    props.forEach { key, value ->
        project.ext[key.toString()] = value
    }
}

generateAndLoadLocalGradleProperties()

