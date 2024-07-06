plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.hiskytechs.autocarehub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hiskytechs.autocarehub"
        minSdk = 25
        targetSdk = 34
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

    implementation("com.airbnb.android:lottie:4.2.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.wear.compose:compose-material:1.2.1")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("androidx.activity:activity:1.9.0")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")
    implementation("androidx.media3:media3-common:1.3.1")
    //implementation("com.google.firebase:firebase-ml-vision:24.1.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.itextpdf:itextpdf:5.5.13.2")
    implementation("com.airbnb.android:lottie:4.2.2")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.hbb20:ccp:2.6.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.3.5")
    androidTestImplementation("androidx.navigation:navigation-testing:2.3.5")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}