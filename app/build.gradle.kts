plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    namespace = "com.sagarmakhija1994.dgnadmin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sagarmakhija1994.dgnadmin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation ("com.airbnb.android:lottie:6.1.0")

    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core-ktx:1.12.0")

    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("androidx.exifinterface:exifinterface:1.3.6")

    //kotlin
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.22")


    //Hilt
    implementation ("com.google.dagger:hilt-android:2.44.2")
    androidTestImplementation ("junit:junit:4.13.2")
    kapt ("com.google.dagger:hilt-android-compiler:2.44.2")
    implementation ("androidx.hilt:hilt-work:1.0.0")

    //Room DB
    implementation ("androidx.room:room-runtime:2.6.0")
    kapt ("androidx.room:room-compiler:2.6.0")
    implementation ("androidx.room:room-ktx:2.6.0")
    implementation ("androidx.room:room-rxjava2:2.6.0")
    implementation ("androidx.room:room-guava:2.6.0")

    //RXnjava
    implementation ("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")

    //Network
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    implementation ("androidx.work:work-runtime-ktx:2.8.1")
    implementation ("androidx.work:work-multiprocess:2.8.1")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    //lifecycle
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    //fragment
    implementation ("androidx.fragment:fragment-ktx:1.7.0-alpha06")

    //FastBle
    //implementation ("com.github.Jasonchenlijian:FastBle:2.4.0")

    //Youtube Player
    //implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    //implementation("com.google.firebase:firebase-auth-ktx")
    //implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage")

    //Youtube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
}