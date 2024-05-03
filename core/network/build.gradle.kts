plugins {
    alias(libs.plugins.eyepetizer.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ekko.api"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(libs.play.services.cronet)
    implementation(projects.core.base)
    ksp(libs.hilt.android.compiler)
    implementation(libs.retrofit)
    implementation(libs.cronet.okhttp)
    implementation(libs.kotlinx.serialization.json)
}