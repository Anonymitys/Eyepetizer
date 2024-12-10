plugins {
    alias(libs.plugins.eyepetizer.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    id("therouter")
}

android {
    namespace = "com.ekko.eyepetizer"

    defaultConfig {
        applicationId = "com.ekko.eyepetizer"
        targetSdk = 35
        versionCode = providers.gradleProperty("version.code").get().toInt()
        versionName = providers.gradleProperty("version.name").get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.fragment.ktx)
    implementation(libs.swiperefreshlayout)
    implementation(libs.coil)
    implementation(libs.pager2Banner)
    implementation(libs.core.splashscreen)
    implementation(projects.core.repository)
    implementation(projects.core.base)
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(projects.core.page)
    implementation(projects.feature.search)
    implementation(projects.feature.playdetail)
    implementation(projects.feature.uploader)
    ksp(libs.router.apt)
    implementation(libs.router)
}