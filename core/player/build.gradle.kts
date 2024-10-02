plugins {
    alias(libs.plugins.eyepetizer.android.library)
    alias(libs.plugins.kotlin.android)
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
    // For media playback using ExoPlayer
    implementation(libs.media3.exoplayer)
    // For DASH playback support with ExoPlayer
    implementation(libs.media3.exoplayer.dash)
    // For HLS playback support with ExoPlayer
    implementation(libs.media3.exoplayer.hls)
    // For SmoothStreaming playback support with ExoPlayer
//    implementation(libs.media3.exoplayer.smoothstreaming)
//    // For RTSP playback support with ExoPlayer
//    implementation(libs.media3.exoplayer.rtsp)
//    // For MIDI playback support with ExoPlayer (see additional dependency requirements in
//    // https://github.com/androidx/media/blob/release/libraries/decoder_midi/README.md)
//    implementation(libs.media3.exoplayer.midi)
//    // For ad insertion using the Interactive Media Ads SDK with ExoPlayer
//    implementation(libs.media3.exoplayer.ima)
//    // For loading data using the Cronet network stack
//    implementation(libs.media3.datasource.cronet)
//    // For loading data using librtmp
//    implementation(libs.androidx.media3.datasource.rtmp)
//    // For building media playback UIs
    implementation(libs.androidx.media3.ui)
//    // For exposing and controlling media sessions
//    implementation(libs.androidx.media3.session)
//    // For extracting data from media containers
//    implementation(libs.androidx.media3.extractor)
//    // For scheduling background operations using Jetpack Work's WorkManager with ExoPlayer
//    implementation(libs.androidx.media3.exoplayer.workmanager)
//    // For transforming media files
//    implementation(libs.androidx.media3.transformer)
//    // For applying effects on video frames
//    implementation(libs.androidx.media3.effect)
//    // For muxing media files
//    implementation(libs.androidx.media3.muxer)
//    // Common functionality for reading and writing media containers
//    implementation(libs.androidx.media3.container)
//    // Common functionality for media database components
//    implementation(libs.androidx.media3.database)
//    // Common functionality for media decoders
//    implementation(libs.androidx.media3.decoder)
//    // Common functionality for loading data
//    implementation(libs.androidx.media3.datasource)
//    // Common functionality used across multiple media libraries
//    implementation(libs.androidx.media3.common)
}