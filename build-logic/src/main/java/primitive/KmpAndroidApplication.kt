package primitive

import me.matsumo.yumemi.codecheck.androidApplication
import me.matsumo.yumemi.codecheck.bundle
import me.matsumo.yumemi.codecheck.implementation
import me.matsumo.yumemi.codecheck.library
import me.matsumo.yumemi.codecheck.libs
import me.matsumo.yumemi.codecheck.setupAndroid
import me.matsumo.yumemi.codecheck.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KmpAndroidApplication : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
                apply("kotlin-parcelize")
                apply("kotlinx-serialization")
                apply("project-report")
                apply("androidx.navigation.safeargs.kotlin")
                apply("com.google.devtools.ksp")
            }

            androidApplication {
                setupAndroid()

                compileSdk = libs.version("compileSdk").toInt()
                defaultConfig.targetSdk = libs.version("targetSdk").toInt()
                buildFeatures.viewBinding = true

                defaultConfig {
                    applicationId = "me.matsumo.yumemi.codecheck"

                    versionName = libs.version("versionName")
                    versionCode = libs.version("versionCode").toInt()
                }

                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "LICENSE",
                            "LICENSE.txt",
                            "NOTICE",
                            "asm-license.txt",
                            "cglib-license.txt",
                            "mozilla/public-suffix-list.txt",
                        )
                    )
                }
            }
        }
    }
}
