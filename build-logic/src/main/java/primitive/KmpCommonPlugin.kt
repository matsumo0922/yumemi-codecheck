package primitive

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

class KmpCommonPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }

            kotlin {
                sourceSets.all {
                    languageSettings {
                        languageVersion = "2.0"

                        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                        optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                        optIn("org.jetbrains.compose.resources.InternalResourceApi")

                        enableLanguageFeature("ExplicitBackingFields")
                    }
                }
            }

            tasks.withType(KotlinCompile::class.java).configureEach {
                kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
                kotlinOptions.freeCompilerArgs += listOf(
                    "-Xuse-k2",
                )
            }

            tasks.withType<KotlinNativeLink>().configureEach {
                notCompatibleWithConfigurationCache("Configuration chache not supported for a system property read at configuration time")
            }
        }
    }
}

fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(action)
}
