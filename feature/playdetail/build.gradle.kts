plugins {
    alias(libs.plugins.eyepetizer.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.ekko.play.detail"

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    implementation(libs.androidx.window)
    ksp(libs.hilt.android.compiler)
    implementation(projects.core.repository)
    implementation(projects.core.player)

    // For media playback using ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.fragment.ktx)
    implementation(projects.core.base)
    implementation(libs.coil)
    ksp(libs.router.apt)
    implementation(libs.router)
    implementation(projects.core.base)
    implementation(libs.androidx.media3.ui)
}