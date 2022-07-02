import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "de.dqmme"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    // PaperMC Dependency
    compileOnly("io.papermc.paper", "paper-api", "1.19-R0.1-SNAPSHOT")

    // KSpigot dependency
    implementation("net.axay", "kspigot", "1.19.0")

    //ProtocolLib dependency
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.8.0")

    // GriefPrevention dependency
    compileOnly("com.github.TechFortress", "GriefPrevention", "16.17.1")

    //NPCLib dependency
    implementation("com.github.juliarn", "npc-lib", "development-SNAPSHOT")

    //OkHttp dependency
    implementation("com.squareup.okhttp3", "okhttp", "4.10.0")

    //AnvilGUI dependency
    implementation("net.wesjd", "anvilgui", "1.5.3-SNAPSHOT")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "18"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
}
