package me.matsumo.yumemi.codecheck

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDetekt() {
    extensions.getByType<DetektExtension>().apply {
        toolVersion = libs.version("detekt")
        // 並列処理
        parallel = true
        // Detektの設定ファイル
        config.setFrom(files("${project.rootDir}/config/detekt/detekt.yml"))
        // デフォルト設定の上に自分の設定ファイルを適用する
        buildUponDefaultConfig = true
        // ルール違反があった場合にfailさせない
        ignoreFailures = false
        // ルール違反の自動修正を試みる
        autoCorrect = false
    }

    tasks.withType<Detekt> {
        exclude {
            it.file.relativeTo(projectDir).startsWith("build")
        }
    }
}
