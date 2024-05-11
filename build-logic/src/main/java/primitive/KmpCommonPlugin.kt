package primitive

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

class KmpCommonPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
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
