plugins {
    id("com.android.application")
    commonPlugins.forEach { id(it) }
    id("androidx.navigation.safeargs.kotlin")
}

//apply(from = "../dependencies.gradle.kts")
configAndroid()
importCommonDependencies()

android {
    defaultConfig {
        applicationId = Versions.App.id
    }
}

dependencies {
    implementation(project(":base"))
    implementation(Dependencies.Navigation.fragmentKtx)
    implementation(Dependencies.Navigation.uiKtx)
}
