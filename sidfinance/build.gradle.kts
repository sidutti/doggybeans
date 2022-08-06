plugins {

    id("java")

}
apply {
    plugin("java")
}
repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }

}
java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch:2.7.2")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    testImplementation("org.slf4j:slf4j-simple:1.7.36")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation("com.google.guava:guava:31.1-jre")
    testImplementation("org.yaml:snakeyaml:1.26")
}

description = "sidfinance"
