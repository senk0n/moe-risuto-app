import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.kpm.external.ExternalVariantApi
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        jvmToolchain(8)
    }

    jvm("desktop")

    js(IR) {
        browser()
        binaries.executable()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Compose application framework"
        homepage = "empty"
        ios.deploymentTarget = "11.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**']"
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.composeImageLoader)
                implementation(libs.napier)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.insetsx)
                implementation(libs.ktor.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.multiplatformSettings)
                implementation(libs.kotlinInject.runtime)
                implementation(libs.decompose)
                implementation(libs.decompose.compose.jetbrains)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(libs.compose.uitooling.preview)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqlDelight.driver.android)
                implementation(libs.decompose.android)
                implementation(libs.kotlinInject.runtime)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqlDelight.driver.sqlite)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(libs.sqlDelight.driver.sqljs)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqlDelight.driver.native)
            }
        }
    }

    commonMainKspDependencies {
        ksp(libs.kotlinInject.compiler)
    }
//    kspDependenciesForAllTargets {
//        ksp(libs.kotlinInject.compiler)
//    }
}

android {
    namespace = "dev.senk0n.moerisuto"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
        targetSdk = 33

        applicationId = "dev.senk0n.moerisuto.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    // comment to enable layout inspector
//    packagingOptions {
//        resources.excludes.add("META-INF/**")
//    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb,
            )
            packageName = "dev.senk0n.moerisuto.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}

sqldelight {
    databases {
        create("MyDatabase") {
            packageName.set("dev.senk0n.moerisuto.db")
        }
    }
}

//dependencies {
//    // KSP will eventually have better multiplatform support and we'll be able to simply have
//    // `ksp libs.kotlinInject.compiler` in the dependencies block of each source set
//    // https://github.com/google/ksp/pull/1021
//    add("kspIosX64", libs.kotlinInject.compiler)
//    add("kspIosArm64", libs.kotlinInject.compiler)
//    add("kspIosSimulatorArm64", libs.kotlinInject.compiler)
//}

//addKspDependencyForAllTargets(libs.kotlinInject.compiler)

fun Project.addKspDependencyForAllTargets(
    dependencyNotation: Any,
) {
    val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()
    dependencies {
        kmpExtension.targets
            .asSequence()
//            .filter { target ->
//                // Don't add KSP for common target, only final platforms
//                target.platformType != KotlinPlatformType.common
//            }
            .filter { target ->
                target.targetName != "metadata"
            }
            .forEach { target ->
                add(
                    "ksp${target.targetName.capitalized()}",
                    dependencyNotation,
                )
            }
    }
}


interface KspDependencies {
    fun ksp(dependencyNotation: Any)
}

fun KotlinTarget.kspDependencies(block: KspDependencies.() -> Unit) {
    val configurationName = "ksp${targetName.capitalized()}"
    project.dependencies {
        object : KspDependencies {
            override fun ksp(dependencyNotation: Any) {
                add(configurationName, dependencyNotation)
            }
        }.block()
    }
}

fun KotlinMultiplatformExtension.kspDependenciesForAllTargets(block: KspDependencies.() -> Unit) {
    targets.configureEach {
        if(targetName != "metadata") {
            kspDependencies(block)
        }
    }
}

fun KotlinMultiplatformExtension.commonMainKspDependencies(block: KspDependencies.() -> Unit) {
    project.dependencies {
        object : KspDependencies {
            override fun ksp(dependencyNotation: Any) {
                add("kspCommonMainMetadata", dependencyNotation)
            }
        }.block()
    }

    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }

    project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
        if(name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}