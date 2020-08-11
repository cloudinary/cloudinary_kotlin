plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // filter in the api credentials before building but without changing original source
        // files - to make sure the credentials are not checked into source control.
        // The url is taken from a property or environment variable:
        manifestPlaceholders = mapOf("cloudinaryUrl" to (getCloudinaryUrl() ?: ""))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("META-INF/proguard/coroutines.pro")
    }

    compileOptions {
        setTargetCompatibility(JavaVersion.VERSION_1_8)
        setSourceCompatibility(JavaVersion.VERSION_1_8)
    }
}

fun getCloudinaryUrl() = findProperty("cloudinaryUrl") ?: System.getenv("CLOUDINARY_URL")

dependencies {
    val workVersion = "2.4.0-beta01"

    api(project(":uploader"))
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.1")
    testImplementation("junit:junit:4.13")

    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation("androidx.work:work-testing:$workVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
