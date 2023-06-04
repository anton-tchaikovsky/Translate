import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.example.translate"
    const val name_space = "com.example.translate"
    const val compile_sdk = 33
    const val min_sdk = 24
    const val target_sdk = 33
    val java_version = JavaVersion.VERSION_17
    const val jvm_target = "17"
    const val version_code = 1
    const val version_name = "1.0"
}

object DependenciesVersions {
    //UI
    const val appcompat = "1.6.1"
    const val material = "1.9.0"
    const val constraintlayout = "2.1.4"
    const val swiperefreshlayout = "1.1.0"

    //Core
    const val core_ktx = "1.10.1"
    const val recyclerview = "1.3.0"
    const val activity_ktx = "1.7.2"

    //Retrofit
    const val retrofit = "2.9.0"

    //Koin
    const val koin = "3.4.0"

    //Coroutines
    const val coroutines = "1.7.1"

    //Coil
    const val coil = "2.4.0"

    //Room
    const val room = "2.5.1"
}

object UI {
    const val appcompat = "androidx.appcompat:appcompat:${DependenciesVersions.appcompat}"
    const val material = "com.google.android.material:material:${DependenciesVersions.material}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${DependenciesVersions.constraintlayout}"
    const val swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${DependenciesVersions.swiperefreshlayout}"
}

object Core {
    const val core_ktx = "androidx.core:core-ktx:${DependenciesVersions.core_ktx}"
    const val recyclerview =
        "androidx.recyclerview:recyclerview:${DependenciesVersions.recyclerview}"
    const val activity_ktx =
        "androidx.activity:activity-ktx:${DependenciesVersions.activity_ktx}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${DependenciesVersions.retrofit}"
    const val converter_gson =
        "com.squareup.retrofit2:converter-gson:${DependenciesVersions.retrofit}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.11.0"
    const val retrofit2_kotlin_coroutines_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
}

object Koin {
    const val koin_core = "io.insert-koin:koin-core:${DependenciesVersions.koin}"
    const val koin_android = "io.insert-koin:koin-android:${DependenciesVersions.koin}"
}

object Coroutines {
    const val kotlinx_coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependenciesVersions.coroutines}"
    const val kotlinx_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependenciesVersions.coroutines}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${DependenciesVersions.coil}"
}

object Room {
    const val room_runtime = "androidx.room:room-runtime:${DependenciesVersions.room}"
    const val room_compiler = "androidx.room:room-compiler:${DependenciesVersions.room}"
}






