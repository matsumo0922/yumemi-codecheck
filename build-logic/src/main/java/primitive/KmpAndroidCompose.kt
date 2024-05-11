package primitive

import me.matsumo.yumemi.codecheck.android
import me.matsumo.yumemi.codecheck.androidTestImplementation
import me.matsumo.yumemi.codecheck.debugImplementation
import me.matsumo.yumemi.codecheck.implementation
import me.matsumo.yumemi.codecheck.library
import me.matsumo.yumemi.codecheck.libs
import me.matsumo.yumemi.codecheck.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KmpAndroidCompose : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
                apply("kotlin-parcelize")
                apply("kotlinx-serialization")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("com.google.devtools.ksp")
                apply("project-report")
            }

            android {
                buildFeatures.compose = true
            }

            dependencies {
                val bom = libs.library("androidx-compose-bom")

                implementation(project.dependencies.platform(bom))
                androidTestImplementation(project.dependencies.platform(bom))

                implementation(libs.library("androidx-compose-runtime"))
                implementation(libs.library("androidx-compose-ui"))
                implementation(libs.library("androidx-compose-ui-util"))
                implementation(libs.library("androidx-compose-ui-binding"))
                implementation(libs.library("androidx-compose-ui-tooling"))
                implementation(libs.library("androidx-compose-ui-tooling-preview"))

                debugImplementation(libs.library("androidx-compose-ui-tooling"))
            }
        }
    }
}
