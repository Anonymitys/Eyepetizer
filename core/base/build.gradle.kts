plugins {
    alias(libs.plugins.eyepetizer.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ekko.base"

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.window)
    implementation(libs.fragment.ktx)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    ksp(libs.router.apt)
    implementation(libs.router)
    implementation(libs.core.splashscreen)
}