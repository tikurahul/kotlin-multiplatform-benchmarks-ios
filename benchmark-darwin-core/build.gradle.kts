import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()
    val xcf = XCFramework("AndroidXBenchmarkXCTest")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        // Generate cinterop bindings for every compilation unit
        it.compilations.forEach { compilation ->
            compilation.cinterops.create("xcTestInterop") {
                defFile = project.file("src/nativeInterop/cinterop/xcTestInterop.def")
                compilerOpts("-DNS_FORMAT_ARGUMENT(A)=")
            }
        }
        it.binaries.framework {
            baseName = "AndroidXBenchmarkXCTest"
            xcf.add(this)
            embedBitcode(BitcodeEmbeddingMode.DISABLE)
        }
    }
    sourceSets {
        val commonMain by getting
        val iosArm64Main by getting {
            dependsOn(commonMain)
        }
        val sourceSets = listOf("iosSimulatorArm64Main", "iosX64Main")
        sourceSets.forEach { name ->
            getByName(name) {
                dependsOn(iosArm64Main)
            }
        }
    }
}
