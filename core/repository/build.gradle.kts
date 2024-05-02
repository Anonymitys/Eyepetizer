plugins {
    alias(libs.plugins.eyepetizer.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.ekko.repository"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(projects.core.api)
    implementation(libs.retrofit)
    ksp(libs.hilt.android.compiler)
    implementation(libs.kotlinx.serialization.json)
}