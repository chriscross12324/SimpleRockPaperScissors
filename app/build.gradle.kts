plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.simplegames.chris.rockpaperscissors"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.simplegames.chris.rockpaperscissors"
        minSdk = 22
        targetSdk = 35
        versionCode = 14
        versionName = "3.2.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
    kotlinOptions {
        jvmTarget = "22"
    }
}

dependencies {
    // Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)

    // UI Libraries
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.gridlayout)

    // Animation Libraries
    implementation(libs.daimajia.easing) { artifact { type = "aar" } }
    implementation(libs.daimajia.animations) { artifact { type = "aar" } }

    // Activity Libraries
    implementation(libs.androidx.activity.ktx)

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.core)
}
