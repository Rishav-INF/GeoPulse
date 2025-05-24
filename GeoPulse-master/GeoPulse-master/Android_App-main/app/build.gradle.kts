plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.geopulse"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.geopulse"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX + Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.jvmstubs)

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Location Services
    implementation(libs.play.services.location)

    // Ads SDK (requires desugar_jdk_libs 2.1.3+)
    implementation(libs.ads.mobile.sdk)

    // Firebase (BOM + Auth + Firestore)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation(libs.googleid)

    // Credential Manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)

    // Desugaring for Java 8+ APIs
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Networking - Ktor
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-android:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Retrofit + Gson + OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Image loading (choose ONE library to avoid duplication — here: Coil + optional Glide)
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.coil-kt:coil-gif:2.4.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    // Lottie animations
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // JetBrains Exposed
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")

    // Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    implementation("androidx.room:room-guava:$room_version")
    implementation("androidx.room:room-paging:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")

        // Maps Compose library for Jetpack Compose integration
    implementation("com.google.maps.android:maps-compose:6.6.0")

        // Google Play Services Maps SDK
    implementation("com.google.android.gms:play-services-maps:18.2.0")

        // Google Play Services Location SDK
    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

}


