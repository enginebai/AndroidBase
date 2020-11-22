# AndroidBase
This `gradle-kotlin-dsl` branch provides a sample to demonstrate how to setup and use **Gradle Kotlin DSL** in the android project.

## Setup
1. Change your project name in `settings.gradle.kts`.
1. Set your application ID in `Versions.kt`
1. Set the package name in `AndroidManifest.xml` file of `:app` module .
1. Select `com.enginebai.project` directory in "Project" tool window and rename package for your app.
1. That's all. Start your app development journey now 🎉.

## Good Practices
There is some practices to follow to take advantage of Gradle Kotlin DSL feature:

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

It's equalivent to the old way `android { ... }` block in `build.gradle` file
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
* `:app` module: That's your app module, just like a normal Android app project. You put all resources that app used, including strings, colors, dimensions, drawables. Or you can create a new modules (ex: `:common`) for that if you use multi-modules project.
* `/buildSrc`: It enables you to write the build script (`*.gradle.kts` files) in kotlin to manage dependencies and gets better IDE completion support. It gives you a way to develop build code more like regular code. More information please check [official document](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources).

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


