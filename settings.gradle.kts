pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/kmp/builds/10252455/artifacts/snapshots/repository")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Benchmarks"
include(":benchmark-darwin-core")
include(":benchmark-darwin")
include(":benchmark-darwin-samples")
