plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

// THIS IS THE DEFINITIVE CHANGE TO FIX THE BUILD ERROR.
// It forces a single version of the conflicting dependency across the entire project.
configurations.all {
    resolutionStrategy {
        force("androidx.coordinatorlayout:coordinatorlayout:1.2.0") // Force a single, stable version
    }
}

android {
    namespace = "com.example.myapplication22"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication22"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
