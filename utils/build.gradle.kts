plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
 namespace = "com.example.utils"
}

dependencies {
    //UI
    implementation (UI.appcompat)
    //Coil
    implementation (Coil.coil)
}