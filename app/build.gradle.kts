plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.fitproappmy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fitproappmy"
        minSdk = 24
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Dependencias esenciales
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Dependencias adicionales para Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2") // Navigation Fragment
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")       // Navigation UI

    // Dependencias de prueba
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
