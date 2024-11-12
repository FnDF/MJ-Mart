plugins {
	id("com.android.application")
	id("com.google.gms.google-services")
}

android {
	namespace = "edu.mj.mart"
	compileSdk = 34

	defaultConfig {
		applicationId = "edu.mj.mart"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation("androidx.appcompat:appcompat:1.7.0")
	implementation("com.google.android.material:material:1.12.0")
	implementation("androidx.activity:activity:1.9.3")
	implementation("androidx.constraintlayout:constraintlayout:2.2.0")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

	implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
	implementation("com.google.firebase:firebase-analytics")
	implementation("com.google.firebase:firebase-auth")

	implementation ("com.sun.mail:android-mail:1.6.0")
	implementation ("com.sun.mail:android-activation:1.6.0")
}