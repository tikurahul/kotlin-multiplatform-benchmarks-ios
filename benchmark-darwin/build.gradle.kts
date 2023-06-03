import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()
    val xcf = XCFramework("AndroidXDarwinBenchmarks")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "AndroidXDarwinBenchmarks"
            xcf.add(this)
            embedBitcode(BitcodeEmbeddingMode.DISABLE)
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }
        val iosArm64Main by getting {
            dependencies {
                api(project(":benchmark-darwin-core"))
            }
        }
        val sourceSets = listOf("iosSimulatorArm64Main", "iosX64Main")
        sourceSets.forEach { name ->
            getByName(name) {
                dependsOn(iosArm64Main)
            }
        }
    }
}
