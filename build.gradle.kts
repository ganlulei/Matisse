import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.maven.publish)
}

group = "com.github.ganlulei"
version = "2.3.2"

android {
    namespace = "github.leavesczy.matisse"
    compileSdk = 36
    buildToolsVersion = "36.1.0"

    defaultConfig {
        minSdk = 23
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }

    lint {
        checkDependencies = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
        optIn.set(
            setOf(
                "androidx.compose.foundation.layout.ExperimentalLayoutApi",
                "com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi"
            )
        )
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    compileOnly(libs.coil.compose)
    compileOnly(libs.glide.compose)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
            }
        }
    }
}