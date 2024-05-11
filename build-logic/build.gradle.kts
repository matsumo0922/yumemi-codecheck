plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.gms.services)
}

gradlePlugin {
    plugins {
        register("KmpPlugin") {
            id = "yumemi.primitive.kmp.common"
            implementationClass = "primitive.KmpCommonPlugin"
        }
        register("KmpAndroidApplication") {
            id = "yumemi.primitive.kmp.android.application"
            implementationClass = "primitive.KmpAndroidApplication"
        }
        register("KmpAndroidLibrary") {
            id = "yumemi.primitive.kmp.android.library"
            implementationClass = "primitive.KmpAndroidLibrary"
        }
        register("KmpAndroidCompose") {
            id = "yumemi.primitive.kmp.android.compose"
            implementationClass = "primitive.KmpAndroidCompose"
        }
        register("DetektPlugin") {
            id = "yumemi.primitive.detekt"
            implementationClass = "primitive.DetektPlugin"
        }
    }
}
