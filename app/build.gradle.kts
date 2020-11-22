plugins {
    id("com.android.application")
    commonPlugins.forEach { id(it) }
}

configAndroid()
importCommonDependencies()

android {
    defaultConfig {
        applicationId = Versions.App.id
    }
}

dependencies {
}
