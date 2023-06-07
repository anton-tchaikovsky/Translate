plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.repository"
}

dependencies {

    implementation(project(Modules.model))
    //Retrofit
    implementation (Retrofit.retrofit)
    implementation (Retrofit.converter_gson)
    //for Retrofit
    implementation (Retrofit.logging_interceptor)
    implementation (Retrofit.retrofit2_kotlin_coroutines_adapter)
    //Coroutines
    implementation (Coroutines.kotlinx_coroutines_core)
    implementation (Coroutines.kotlinx_coroutines_android)
    //Room
    implementation (Room.room_runtime)
    kapt (Room.room_compiler)

}