
plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("kotlin-android")

}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId("com.elouyi.yuiue")
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "0.1 alpha"
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin","kotlin-stdlib","1.4.21")
    implementation("androidx.core","core-ktx","1.3.2")
    implementation("androidx.appcompat","appcompat","1.2.0")
    implementation("androidx.constraintlayout","constraintlayout","2.0.4")
    implementation("androidx.lifecycle","lifecycle-livedata","2.2.0")
    implementation("androidx.lifecycle","lifecycle-extensions","2.2.0")
    implementation("androidx.lifecycle","lifecycle-viewmodel-ktx","2.2.0")
    implementation("androidx.lifecycle","lifecycle-viewmodel-savedstate","2.2.0")
    implementation("androidx.activity","activity-ktx","1.1.0")
    implementation("com.google.android.material","material","1.1.0")
    implementation("com.squareup.retrofit2","retrofit","2.9.0")
    implementation("com.squareup.retrofit2","converter-gson","2.9.0")
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-core","1.4.2")
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-android","1.4.2")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}