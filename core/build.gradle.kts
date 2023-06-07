plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.core"
}

dependencies {

    implementation(project(Modules.model))
    implementation(project(Modules.repository))
    implementation(project(Modules.translate_foto_screen))

    //UI
    implementation (UI.appcompat)
    implementation (UI.material)
    implementation (UI.constraintlayout)
    //Core
    implementation (Core.core_ktx)
    implementation (Core.recyclerview)
    implementation (Core.activity_ktx)
    //Coroutines
    implementation (Coroutines.kotlinx_coroutines_core)
    implementation (Coroutines.kotlinx_coroutines_android)

}