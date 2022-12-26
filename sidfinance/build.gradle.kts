plugins {
    id("java")
    id ("io.spring.dependency-management"). version ("1.1.0")
    id ("org.springframework.boot").version("3.0.0").apply(false)
}
apply {
    plugin("java")
    plugin("io.spring.dependency-management")

}
repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }

}
java.sourceCompatibility = JavaVersion.VERSION_17
dependencyManagement{
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}
dependencies {
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.slf4j:slf4j-api")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("ch.qos.logback:logback-classic:")
    testImplementation("org.slf4j:slf4j-simple")
    testImplementation("junit:junit")
    testImplementation("com.squareup.okhttp3:mockwebserver")
    testImplementation("com.google.guava:guava")
    testImplementation("org.yaml:snakeyaml")
}

description = "sidfinance"
