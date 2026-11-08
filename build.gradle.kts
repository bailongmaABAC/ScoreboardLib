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
    // 服务端 API 与 Via 的 API 由运行环境提供，声明成 compileOnly 才不会写进 POM 变成消费者的运行时依赖。
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("com.viaversion:viaversion-api:4.0.0")
}


group = "me.tigerhix.lib"
version = "1.4.5-1"
description = "scoreboard"
java {
    // paper-api 1.21.11 的 class 文件是 Java 21，target 定在 8 会让 Gradle 变体解析要求 JVM 8 实现而失败。
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}


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