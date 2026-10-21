import com.android.build.api.dsl.ApplicationExtension
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

extensions.configure<ApplicationExtension> {
    namespace = "com.simplegames.chris.rockpaperscissors"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.simplegames.chris.rockpaperscissors"
        minSdk = 31
        targetSdk = 37
        versionCode = 15
        versionName = "4.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val dateFormat = SimpleDateFormat("MMM dd, yyyy")
        val buildDate = dateFormat.format(Date())
        buildConfigField("String", "BUILD_DATE", "\"$buildDate\"")
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // Core Libraries
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // UI Libraries
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)

    // Animation Libraries
    implementation(libs.daimajia.animations) { artifact { type = "aar" } }
    implementation(libs.daimajia.easing) { artifact { type = "aar" } }

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.core)
}
