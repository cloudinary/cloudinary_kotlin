buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.50"))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
