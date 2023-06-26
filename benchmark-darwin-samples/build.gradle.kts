import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("androidx.benchmark.darwin")
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
            export("androidx.benchmark:benchmark-darwin:1.2.0-SNAPSHOT")
            embedBitcode(BitcodeEmbeddingMode.DISABLE)
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                api("androidx.benchmark:benchmark-darwin:1.2.0-SNAPSHOT")
            }
        }
        val iosArm64Main by getting
        val sourceSets = listOf("iosSimulatorArm64Main", "iosX64Main")
        sourceSets.forEach { name ->
            getByName(name) {
                dependsOn(iosArm64Main)
            }
        }
    }
}

darwinBenchmark {
    xcodeGenConfigFile.set(
        project.rootProject.file("benchmark-darwin-xcode/projects/benchmark-darwin-samples-xcode.yml")
    )
    xcodeProjectName.set("benchmark-darwin-samples")
    scheme.set("testapp-ios")
    destination.set("platform=iOS Simulator,name=iPhone 13,OS=15.2")
}
