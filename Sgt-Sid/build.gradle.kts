plugins {
    id("org.springframework.boot").version("3.0.0")
    id("io.spring.dependency-management").version("1.1.0")
    id("java")
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
}
apply {
    plugin("java")
    plugin("org.springframework.boot")
}
repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }

}
java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(project(":sidfinance"))
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("ch.qos.logback:logback-classic")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

description = "Sgt-Sid"
