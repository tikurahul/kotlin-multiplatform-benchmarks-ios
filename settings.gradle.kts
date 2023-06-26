pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/kmp/builds/10385446/artifacts/snapshots/repository")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/kmp/builds/10385446/artifacts/snapshots/repository")
        }
    }
}

rootProject.name = "Benchmarks"
include(":benchmark-darwin-samples")
