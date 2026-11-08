plugins {
    id("signing")
    `maven-publish`
    java
}

repositories {
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.viaversion.com")
}

dependencies {
    implementation("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    implementation("com.viaversion:viaversion-api:4.0.0")
}


group = "me.tigerhix.lib"
version = "1.4.5"
description = "scoreboard"
java.sourceCompatibility = JavaVersion.VERSION_1_8


tasks {
    processResources {
        filesMatching("**/plugin.yml") {
            expand(project.properties)
        }
    }

    javadoc {
        options.encoding = "UTF-8"
    }

}

publishing {
    repositories {
        maven {
            name = "Releases"
            url = uri("https://maven.plugily.xyz/releases")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
        maven {
            name = "Snapshots"
            url = uri("https://maven.plugily.xyz/snapshots")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}