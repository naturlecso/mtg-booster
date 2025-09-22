# MTG Booster Generator

A tool to generate booster packs for *Magic: The Gathering*.

---

## About

**MTG Booster Generator** is a KMM (Kotlin Multiplatform Mobile) project that helps with creating randomized booster packs for *Magic: The Gathering*.
The main purpose of the project is trying out new KMM technologies/architectures/libraries.

⚠️ Project is under development, some things work, some not. ⚠️

![Screenshot of the app.](/assets/screenshot_1.png)
![Screenshot of the app.](/assets/screenshot_2.png)

---

## Project Setup & Environment

[JetBrains Amper](https://github.com/JetBrains/amper/) is an experimental build tool designed to simplify project configuration for Kotlin and Java applications.
For setup check the [official documentation](https://github.com/JetBrains/amper/blob/release/0.7/docs/Setup.md)

### Run Android project

```bash
   ./amper run -p android
```

### Run iOS project
❗Currently broken because of a [Room bug](https://youtrack.jetbrains.com/issue/AMPER-4398/Room-KSP-iOS-Build-Failure)❗

```bash
   ./amper run --module ios-app --platform iosSimulatorArm64
```

## Libraries

* [Coroutines](https://github.com/Kotlin/kotlinx.coroutines#multiplatform) - Concurrency & Threading
* [DataStore Preferences](https://developer.android.com/kotlin/multiplatform/datastore) - Data storage
* [Decompose](https://arkivanov.github.io/Decompose/) - Kotlin Multiplatform library for breaking down your code into lifecycle-aware
  business logic components (aka BLoC).
* [Kermit](https://kermit.touchlab.co/) - Logging
* [kotlin-inject-anvil](https://github.com/amzn/kotlin-inject-anvil?tab=readme-ov-file) - Dependency Injection library.
* [Kotlinx Serialization](https://ktor.io/docs/kotlin-serialization.html) - De/Serializing JSON
* [Ktor](https://ktor.io/) - Networking
* [Room](https://developer.android.com/kotlin/multiplatform/room) - Database
* [Store5](https://store.mobilenativefoundation.org/docs/meet-store) - Data store
* [JetBrains Compose](https://www.jetbrains.com/compose-multiplatform/) - UI
* [Compose Unstyled](https://composables.com/docs/compose-unstyled/components) - Compose components
* [Coil](https://coil-kt.github.io/coil/compose/) - Image loading
