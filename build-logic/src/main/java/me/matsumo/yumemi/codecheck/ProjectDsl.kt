package me.matsumo.yumemi.codecheck

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

internal fun Project.androidExt(configure: BaseExtension.() -> Unit) {
    (this as ExtensionAware).extensions.configure("android", configure)
}

internal fun Project.commonExt(configure: CommonExtension<*, *, *, *, *, *>.() -> Unit) {
    val plugin = if (isApplicationProject()) BaseAppModuleExtension::class.java else LibraryExtension::class.java
    (this as ExtensionAware).extensions.configure(plugin, configure)
}

internal fun Project.isApplicationProject(): Boolean {
    return project.extensions.findByType(BaseAppModuleExtension::class.java) != null
}

internal fun Project.isLibraryProject(): Boolean {
    return project.extensions.findByType(LibraryExtension::class.java) != null
}
