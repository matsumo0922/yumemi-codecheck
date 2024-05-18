package primitive

import me.matsumo.yumemi.codecheck.androidApplication
import me.matsumo.yumemi.codecheck.libs
import me.matsumo.yumemi.codecheck.setupAndroid
import me.matsumo.yumemi.codecheck.version
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpAndroidApplication : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
                apply("kotlin-parcelize")
                apply("kotlinx-serialization")
                apply("project-report")
                apply("com.google.devtools.ksp")
                apply("com.mikepenz.aboutlibraries.plugin")
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
