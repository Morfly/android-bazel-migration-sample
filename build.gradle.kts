buildscript {
    val kotlinVersion by extra("1.5.10")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

apply<MigrationPlugin>()

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}