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
}

fun getCloudinaryUrl() = findProperty("cloudinaryUrl") ?: System.getenv("CLOUDINARY_URL")

dependencies {
    api(project(":url-gen"))
    implementation(kotlin("stdlib"))

    compileOnly("com.squareup.picasso:picasso:2.71828")
    compileOnly("com.facebook.fresco:fresco:2.2.0")
    compileOnly("com.github.bumptech.glide:glide:4.11.0")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    androidTestImplementation("org.mockito:mockito-android:2.24.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test:runner:1.2.0")

}
