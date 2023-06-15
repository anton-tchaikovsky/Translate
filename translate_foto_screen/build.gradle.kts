plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.translate_foto_screen"
}

dependencies {
    implementation(project(Modules.utils))
    //UI
    implementation (UI.appcompat)
    implementation (UI.material)
    implementation (UI.constraintlayout)
    implementation (UI.swiperefreshlayout)
    //Koin
    implementation (Koin.koin_core)
    implementation (Koin.koin_android)
}