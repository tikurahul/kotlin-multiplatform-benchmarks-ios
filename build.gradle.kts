plugins {
    id("com.android.application").version("8.2.0-alpha06").apply(false)
    id("com.android.library").version("8.2.0-alpha06").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
    // Benchmark Gradle Plugin
    id("androidx.benchmark.darwin").version("1.2.0-SNAPSHOT").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
