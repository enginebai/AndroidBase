plugins {
    id("com.android.library")
    commonPlugins.forEach { id(it) }
    id("com.github.dcendents.android-maven")
}

group = "com.github.enginebai"

configAndroid()
importCommonDependencies()

dependencies {
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLogging)
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.Retrofit.rxJavaAdapter)
}

