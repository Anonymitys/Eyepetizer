plugins {
    alias(libs.plugins.eyepetizer.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ekko.search"

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.swiperefreshlayout)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(projects.core.page)
    implementation(projects.core.repository)
    implementation(libs.coil)
    implementation(projects.core.base)
    implementation(libs.fragment.ktx)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(kotlin("reflect"))
}