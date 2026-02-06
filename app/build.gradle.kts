plugins {
    application
    checkstyle
    id("org.sonarqube") version "7.0.1.6134"
    id ("com.github.ben-manes.versions") version "0.53.0"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass = "hexlet.code.App"
}

tasks.test {
    useJUnitPlatform()
}

sonar {
    properties {
        property("sonar.projectKey", "SergeyKkk1_java-project-71")
        property("sonar.organization", "sergeykkk1")
    }
}