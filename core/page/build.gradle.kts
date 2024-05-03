plugins {
    alias(libs.plugins.eyepetizer.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ekko.page"

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(projects.core.base)
    implementation(libs.coil)
    implementation(projects.core.repository)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.pager2Banner)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.swiperefreshlayout)
    ksp(libs.hilt.android.compiler)
    implementation(libs.fragment.ktx)
}