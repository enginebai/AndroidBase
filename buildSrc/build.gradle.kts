repositories {
    mavenCentral()
    google()
}

plugins {
    `kotlin-dsl`
}

//gradlePlugin {
//    plugins {
//        register("common-plugin") {
//            id = "common-plugin"
//            implementationClass = "CommonPlugin"
//        }
//    }
//}

dependencies {
    // Depend on the android gradle plugin, since we want to access it in our plugin
    implementation("com.android.tools.build:gradle:7.0.2")

    // Depend on the kotlin plugin, since we want to access it in our plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")

    // Depend on the default Gradle API since we want to build a custom plugin
    implementation(gradleApi())
    implementation(localGroovy())
}