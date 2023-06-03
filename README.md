# Kotlin Multiplatform Benchmarks for iOS

This repository is an example for running Kotlin Multiplatform Mobile benchmarks on
Darwin platforms (iOS, watchOS, tvOS etc.).

## Writing Benchmarks

### Project Setup

To create new benchmarks, look at the example `benchmark-darwin-samples` gradle module.
You should create a new module which follows the same convention as the sample.

```markdown
Note:
The packaged `XCFramework` should always be called `AndroidXDarwinBenchmarks` by convention.
```

This is done using:

```kotlin
// The name of the task that assembles the eventual XCFramework will always be called
// assembleAndroidXDarwinBenchmarksXCFramework
val xcf = XCFramework("AndroidXDarwinBenchmarks")
```

If you want to define additional iOS specific dependencies that should be done using the following
dependencies block.

```kotlin
val iosArm64Main by getting {
    dependencies {
        api(project(":benchmark-darwin"))
        // Other dependencies go here
    }
}
```

### YAML Configuration

The `androidx.benchmark.darwin` plugin automatically generates an Xcode project using `xcodegen`.
This makes it convenient to run the benchmarks from Xcode if necessary.

A sample YAML configuration is [here](benchmark-darwin-xcode/projects/benchmark-darwin-samples-xcode.yml).

The key things to change when creating a new configuration are

#### Project name

```yaml
name: benchmark-darwin-samples
# More stuff ...
```

The name should match the module name defined in the `darwinBenchmark` plugin block.

```kotlin
darwinBenchmark {
    xcodeGenConfigFile.set(
        // The location of the new YAML file
        project.rootProject.file("benchmark-darwin-xcode/projects/benchmark-darwin-samples-xcode.yml")
    )
    // The name of the project should match the one defined in the YAML
    xcodeProjectName.set("benchmark-darwin-samples")
    scheme.set("testapp-ios")
    destination.set("platform=iOS Simulator,name=iPhone 13,OS=15.2")
}
```

#### XCFramework dependency

The YAML configuration additionally sets up the location of the generated XCFramework containing
the benchmarks.

```yaml
# ...
dependencies:
  # Change module name from benchmark-darwin-samples to the one you defined.
  # The name of the xcframework is always going to be AndroidXDarwinBenchmarks by convention. 
  - framework: "${PROJECT_DIR}/../../benchmark-darwin-samples/build/XCFrameworks/release/AndroidXDarwinBenchmarks.xcframework"
# ...
```

### Running Benchmarks

Running benchmarks is as simple as running

```bash
./gradlew :benchmark-darwin-samples:darwinBenchmarkResults
```

Once you run the benchmarks, you should see the benchmark results in the `build` folder in a file
that's named `module-name-benchmark-results.json`.

The `androidx.darwin.benchmark` plugin takes care of parsing the `xcresult` files, and generates 
a friendlier benchmark result that can be consumed by other tools for regression detection.

Example output looks something like:

```json
{
  "key": {
    "destination": "iPhone 13",
    "arch": "arm64",
    "targetSdk": "iphonesimulator16.4",
    "identifier": "00006001-001C61522185801E",
    "modelName": "MacBook Pro",
    "modelCode": "MacBookPro18,2"
  },
  "results": [
    {
      "key": {
        "testDescription": "Allocate an ArrayList of size 1000",
        "metricName": "Memory Physical",
        "metricIdentifier": "com.apple.dt.XCTMetric_Memory.physical",
        "polarity": "prefers smaller",
        "units": "kB"
      },
      "measurements": {
        "stat": [
          {
            "value": "min",
            "measurement": 0.0
          },
          {
            "value": "median",
            "measurement": 16.384
          },
          {
            "value": "max",
            "measurement": 131.072
          },
          {
            "value": "stddev",
            "measurement": 55.560847221762195
          }
        ]
      }
    },
    {
      "key": {
        "testDescription": "Allocate an ArrayList of size 1000",
        "metricName": "Clock Monotonic Time",
        "metricIdentifier": "com.apple.dt.XCTMetric_Clock.time.monotonic",
        "polarity": "prefers smaller",
        "units": "s"
      },
      "measurements": {
        "stat": [
          {
            "value": "min",
            "measurement": 2.57644E-4
          },
          {
            "value": "median",
            "measurement": 3.17668E-4
          },
          {
            "value": "max",
            "measurement": 3.9798700000000004E-4
          },
          {
            "value": "stddev",
            "measurement": 5.93653765649642E-5
          }
        ]
      }
    },
    {
      "key": {
        "testDescription": "Allocate an ArrayList of size 1000",
        "metricName": "CPU Cycles",
        "metricIdentifier": "com.apple.dt.XCTMetric_CPU.cycles",
        "polarity": "prefers smaller",
        "units": "kC"
      },
      "measurements": {
        "stat": [
          {
            "value": "min",
            "measurement": 2234.267
          },
          {
            "value": "median",
            "measurement": 2589.556
          },
          {
            "value": "max",
            "measurement": 3179.896
          },
          {
            "value": "stddev",
            "measurement": 346.9417510572922
          }
        ]
      }
    },
    {
      "key": {
        "testDescription": "Allocate an ArrayList of size 1000",
        "metricName": "CPU Time",
        "metricIdentifier": "com.apple.dt.XCTMetric_CPU.time",
        "polarity": "prefers smaller",
        "units": "s"
      },
      "measurements": {
        "stat": [
          {
            "value": "min",
            "measurement": 7.236090000000001E-4
          },
          {
            "value": "median",
            "measurement": 8.37343E-4
          },
          {
            "value": "max",
            "measurement": 0.0010194240000000001
          },
          {
            "value": "stddev",
            "measurement": 1.0913249889148513E-4
          }
        ]
      }
    },
    {
      "key": {
        "testDescription": "Allocate an ArrayList of size 1000",
        "metricName": "CPU Instructions Retired",
        "metricIdentifier": "com.apple.dt.XCTMetric_CPU.instructions_retired",
        "polarity": "prefers smaller",
        "units": "kI"
      },
      "measurements": {
        "stat": [
          {
            "value": "min",
            "measurement": 5536.699
          },
          {
            "value": "median",
            "measurement": 5831.332
          },
          {
            "value": "max",
            "measurement": 6093.972
          },
          {
            "value": "stddev",
            "measurement": 198.4206625064538
          }
        ]
      }
    },
    {
      "key": {
        "testDescription": "Allocate an ArrayList of size 1000",
        "metricName": "Memory Peak Physical",
        "metricIdentifier": "com.apple.dt.XCTMetric_Memory.physical_peak",
        "polarity": "prefers smaller",
        "units": "kB"
      },
      "measurements": {
        "stat": [
          {
            "value": "min",
            "measurement": 28905.152
          },
          {
            "value": "median",
            "measurement": 28970.688
          },
          {
            "value": "max",
            "measurement": 28987.072
          },
          {
            "value": "stddev",
            "measurement": 33.97458551329278
          }
        ]
      }
    }
  ],
  "version": 1
}
```