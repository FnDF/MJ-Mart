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

	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)

	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.analytics)
	implementation(libs.firebase.auth)
	implementation(libs.firebase.firestore)

	implementation (libs.android.mail)
	implementation (libs.android.activation)

	implementation(libs.androidx.navigation.fragment)
	implementation(libs.androidx.navigation.ui)

	// Use this dependency to use the dynamically downloaded model in Google Play Services
	implementation(libs.play.services.mlkit.barcode.scanning)
	implementation(libs.play.services.code.scanner)

	implementation(libs.zxing.core)
}