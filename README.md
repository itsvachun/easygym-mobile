# EasygymMobile

This project is an Android application written in **Kotlin** using **Jetpack Compose** for the user interface — the modern declarative UI toolkit for Android.

## Development Environment

To start working locally, open the project in **Android Studio** (with Compose support).

### 🚀 Running the App

Once the project is open:

1. Connect an Android device or start an emulator.
2. Press **Run** in Android Studio, or use:

```bash
./gradlew installDebug
```

The app will launch on your connected device/emulator.

## Building

To build the project:

```bash
./gradlew assemble
```

This compiles your code and outputs build artifacts under `app/build/`.

## Running Unit Tests

To execute unit tests with JUnit:

```bash
./gradlew test
```

## Running Instrumented (UI) Tests

To run tests on a physical device or emulator:

```bash
./gradlew connectedAndroidTest
```

## Additional Resources

For more information about using Jetpack Compose, including detailed references and samples, see the official documentation:

* Jetpack Compose documentation and samples — official Android developer site.
