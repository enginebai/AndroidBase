![Language](https://img.shields.io/badge/language-kotlin-blue?logo=kotlin) ![License](https://img.shields.io/badge/License-MIT-orange) [![](https://jitpack.io/v/enginebai/AndroidBase.svg)](https://jitpack.io/#enginebai/AndroidBase)

# AndroidBase
The `AndroidBase` project provides an Android app project template that setups for **Gradle Kotlin DSL**, it also provides some base classes or extensions to accerate your android development.

You can [use this template](https://github.com/enginebai/AndroidBase/generate) or download the [base module](#Download-for-Base-Module).

There also provides two helper branches: 
* If you'd like to see how the simple Gradle Kotlin DSL project works (i.e. a new android app project just created by Android Studio and use Gradle Kotlin DSL), you can checkout to the branch [`gradle-kotlin-dsl`](https://github.com/enginebai/AndroidBase/tree/gradle-kotlin-dsl) to take a look.
* If you'd like to see how the base module works or contribute to this project, you can checkout to the branch [`library`](https://github.com/enginebai/AndroidBase/tree/library).

## Setup for Template Project
1. Just click on [![Clone this template](https://img.shields.io/badge/-Clone%20template-brightgreen)](https://github.com/enginebai/AndroidBase/generate) button to create a new repo starting from this template. Or you can clone this project by `git clone git@github.com:enginebai/AndroidBase.git` .
1. Change your project name in `settings.gradle.kts`.
1. Set your application ID in `Versions.kt`
1. Set the package name in `AndroidManifest.xml` file of `:app` module .
1. Select `com.enginebai.project` directory in "Project" tool window and rename package for your app.
1. Specify your retrofit base URL in `NetworkModule.kt` file.
1. Start to design your main layout xml file `fragment_main.xml` and fragment class.
1. Specify your `MainFragment.kt` name in navigation graph xml file.
1. That's all. Start your app development journey now 🎉.

## Get Started with Template Project
1. You can start your development as usual in `app` module.
1. This projects encourges you to use single activity architecture with [naivgation component](https://developer.android.com/guide/navigation), you will create new fragment that **extends the `BaseFragment`** for your all UI pages.
1. This project uses [koin](https://github.com/InsertKoinIO/koin) as our dependency injection framework, you will define the modules in `di` package and add those modules in `AppContext.defineDependencies()`
1. You will handle errors with `ExceptionHandler`, we register a function that will handle errors that are passed to `Subscriber.onError(Throwable)` for RxJava; for non-RxJava exception, you will inject `ExceptionHandler` and pass exception to `accept(Throwable)` function. More detail usage you can check `ExceptionHandler`, there are some  instructions that guide you how to write your custom exception handling logic.

## Good Practices of Gradle Kotlin DSL
* Add all dependencies versions in `Versions.kt` 

```kotlin
object Versions {
    const val kotlin = "1.3.50"
    const val awesomeLibrary = "x.y.z"
    // TODO: add the library version
    ...
}
```
* Define all 3rd-party dependencies in `Dependencies.kt`, and use all versions definition in `Versions.kt`.

```kotlin
object Dependencies {
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    // TODO: add standalone dependency here!
    ...

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }

    // TODO: add inner object for sub-modules of library
    object AndroidX {
        ...
    }
    ...
}
```

* Always import dependency from `Dependencies.kt` in `build.gradle.kts` file.

```kotlin
dependencies {
	implementation(Dependencies.Glide.core)
	"kapt"(Dependencies.Glide.compiler)
    implementation(project(":base"))
    // TODO: add by using dependency imported from `Dependencies.kt` file
    ...
}
```

* Configure android build script in `Config.kt`.

```kotlin
fun Project.configAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(Versions.Android.sdk)
    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.sdk)
        versionCode = Versions.App.versionCode
        versionName = Versions.App.versionName
        // TODO: add your configurations
        ...
    }
    ...
}
```

It's equivalent to the old way `android { ... }` block in `build.gradle` file
```groovy
android {
    compileSdkVersion 21
    buildToolsVersion "21.1.2"
    defaultConfig {
        applicationId "com.enginebai.moviehunt"
        // TODO: add your configurations
        ...
    }
    ...
}
``` 


* Add all configuration variables inside `Config` object in `Config.kt`, and add `buildConfigField(...)` to include.

```kotlin
object Config {
    const val API_ROOT = "\"https://api.themoviedb.org/3/\""
    const val IMAGE_API_ROOT = "\"https://image.tmdb.org/t/p/\""
    // TODO: add your constants here, make sure to add extra double quotes for string value.
}

fun Project.configAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(Versions.Android.sdk)
    defaultConfig {
        ...

        buildConfigField("String", "API_ROOT", Config.API_ROOT)
        buildConfigField("String", "IMAGE_API_KEY", Config.IMAGE_API_ROOT)
        // TODO: add your varialbes here imported from `Config` object
        ... 
    }
    ...
}
```

* Add the common dependencies that share between modules to `Dependencies.kt`

```kotlin
fun Project.importCommonDependencies() {
    dependencies {
        ...
        implementation(Dependencies.material)
        // TODO: add your common dependencies
        .. 
    }
}
```

> **Note:** Remember to perform Gradle Sync to apply your changes when updating any files in `buildSrc`.

## Modules Structure
* `:app` module: That's your app module, just like a normal Android app project. Or you can create a new modules (ex: `:common`) for that if you use multi-modules project or libraries. There is the sample `build.gradle.kts` for library module:

```kotlin
plugins {
    id("com.android.library")
    commonPlugins.forEach { id(it) }
}

configAndroid()
importCommonDependencies()

dependencies {
    implementation(Dependencies.whatever)
    ...
}
```
* `/buildSrc`: It enables you to write the build script (`*.gradle.kts` files) in kotlin to manage dependencies and gets better IDE completion support. It gives you a way to develop build code more like regular code. More information please check [official document](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources).

## Download for Base Module
Step 1. Add it to your root `build.gradle.kts`:

```kotlin
allprojects {
    repositories {
        ...
        maven("https://jitpack.io")
    }
}
```
Step 2. Add the dependency:

* `Versions.kt`:
```kotlin
const val androidBase = "1.0.0"
```

* `Dependencies.kt`:
```kotlin
const val androidBase = "com.github.enginebai:AndroidBase:${Versions.androidBase}"
```

* App module `build.gradle.kts`:
```kotlin
dependencies {
    ...
    implementation(Dependencies.androidBase)
}
```

## Included Libraries
There are some default 3rd-party libraries imported in this project, and provide some popular dependencies (following listed) in `buildSrc/Dependencies.kt` file that you can choose to use. Feel free to add/remove those dependencies.

* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture), part of Android Jetpack for give to project a robust design, testable and maintainable.
* [Retrofit](https://github.com/square/retrofit) / [OkHttp](https://github.com/square/okhttp), Square open-source RESTful API and http client.
* [RxJava](https://github.com/ReactiveX/RxJava/) / [RxAndroid](https://github.com/ReactiveX/RxAndroid), reactive programming for JVM.
* [Koin](https://github.com/InsertKoinIO/koin), kotlin light-weight dependency injection.
* [Timber](https://github.com/JakeWharton/timber), for logging.
* [Epoxy](https://github.com/airbnb/epoxy), for RecyclerView complex view layout.
* [Paging](https://developer.android.com/topic/libraries/architecture/paging), for pagination loading of RecyclerView.
* [Navigation](https://developer.android.com/guide/navigation), for single activity and fragment routing.
* [Room](https://developer.android.com/training/data-storage/room), for local persistence database.

## Useful Extensions
* See [Extension Functions.](./EXTENSIONS.md)

## LICENSE

```
Copyright (c) 2020 Engine Bai

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


