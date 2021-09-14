plugins {
    id("com.android.library")
    commonPlugins.forEach { id(it) }
    id("maven-publish")
}

group = "com.github.enginebai"

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("library") {
                groupId = "com.github.enginebai"
                artifactId = "AndroidBase"
                version = "1.0.1"
                // base-release.aar = $moduleName-$buildType.aar
                artifact("$buildDir/outputs/aar/base-release.aar")
            }
        }
    }
}

configAndroid()
importCommonDependencies()

dependencies {
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLogging)
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.Retrofit.rxJavaAdapter)
}

