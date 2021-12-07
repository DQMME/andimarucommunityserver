import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
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
    compileOnly("com.github.TechFortress:GriefPrevention:16.17.1")

    //NPCLib dependency
    implementation("com.github.juliarn:npc-lib:development-SNAPSHOT")
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
