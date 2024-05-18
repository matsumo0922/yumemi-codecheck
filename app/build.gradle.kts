
import com.android.build.api.variant.ResValue
import me.matsumo.yumemi.codecheck.testImplementation
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("yumemi.primitive.kmp.common")
    id("yumemi.primitive.kmp.android.application")
    id("yumemi.primitive.kmp.android.compose")
    id("yumemi.primitive.detekt")
}

android {
    namespace = "me.matsumo.yumemi.codecheck"

    val localProperties = Properties().apply {
        load(project.rootDir.resolve("local.properties").inputStream())
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("${project.rootDir}/gradle/keystore/debug.keystore")
        }
        create("release") {
            // 本来は秘匿すべきファイルであることは承知しているが、リリースビルドでの動作確認のために commit している
            storeFile = file("${project.rootDir}/gradle/keystore/release.keystore")
            storePassword = localProperties.getProperty("storePassword") ?: System.getenv("RELEASE_STORE_PASSWORD") // yumemi
            keyPassword = localProperties.getProperty("keyPassword") ?: System.getenv("RELEASE_KEY_PASSWORD") // yumemi
            keyAlias = localProperties.getProperty("keyAlias") ?: System.getenv("RELEASE_KEY_ALIAS") // key0
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            versionNameSuffix = ".D"
            applicationIdSuffix = ".debug"
        }
    }

    androidComponents {
        onVariants {
            val appName = "Yumemi Codecheck" + if (it.buildType == "debug") " Debug" else ""

            it.resValues.apply {
                put(it.makeResValueKey("string", "app_name"), ResValue(appName, null))
            }

            if (it.buildType == "release") {
                it.packaging.resources.excludes.add("META-INF/**")
            }
        }
    }

    dependencies {
        // data store
        implementation(libs.androidx.datastore)
        implementation(libs.androidx.datastore.proto)
        api(libs.androidx.datastore.preferences)

        // ui
        implementation(libs.androidx.core)
        implementation(libs.androidx.core.splashscreen)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.activity.compose)

        implementation(libs.uuid)
        implementation(libs.ksoup)
        implementation(libs.webview.compose)
        implementation(libs.libraries.ui)
        implementation(libs.libraries.core)

        api(project.dependencies.platform(libs.kotlin.bom))
        api(project.dependencies.platform(libs.koin.bom))

        api(libs.bundles.infra.api)
        api(libs.bundles.ui.common.api)
        api(libs.bundles.ui.android.api)
        api(libs.bundles.koin)
        api(libs.bundles.ktor)

        testImplementation(libs.bundles.kotest)
        testImplementation(libs.bundles.mockk)
    }
}
