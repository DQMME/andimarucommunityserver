import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
}

group = "de.dqmme"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://jitpack.io")
}

dependencies {
    // PaperMC Dependency
    compileOnly("io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")

    // KSpigot dependency
    implementation("net.axay:kspigot:1.17.4")

    // GriefPrevention dependency
    implementation("com.github.TechFortress:GriefPrevention:16.17.1")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}
