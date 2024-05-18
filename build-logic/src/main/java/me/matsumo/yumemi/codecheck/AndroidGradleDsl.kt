package me.matsumo.yumemi.codecheck

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

fun Project.androidApplication(action: BaseAppModuleExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.android(action: TestedExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.setupAndroid() {
    android {
        defaultConfig {
            targetSdk = libs.version("targetSdk").toInt()
            minSdk = libs.version("minSdk").toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        splits {
            abi {
                isEnable = true
                isUniversalApk = true

                reset()
                include("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }

        dependencies {
            testImplementation(libs.library("junit"))
            androidTestImplementation(libs.library("androidx-test-ext-junit"))
            androidTestImplementation(libs.library("androidx-test-espresso-espresso-core"))
            add("coreLibraryDesugaring", libs.library("desugar"))
        }
    }
}
