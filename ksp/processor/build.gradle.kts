plugins {
    kotlin("jvm")

}

dependencies {
    implementation(libs.symbol.processing.api)
    implementation(projects.ksp.annotation)
    implementation(libs.kotlinpoet.ksp)
}
