package me.matsumo.yumemi.codecheck

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
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

    val reportMerge = rootProject.tasks.register("reportMerge", ReportMergeTask::class) {
        output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml"))
    }

    tasks.withType<Detekt> {
        finalizedBy(reportMerge)

        reportMerge.configure {
            input.from(xmlReportFile) // or .sarifReportFile
        }

        exclude {
            it.file.relativeTo(projectDir).startsWith("build")
        }
    }
}
