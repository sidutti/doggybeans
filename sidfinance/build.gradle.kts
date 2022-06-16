
plugins {
    //id ("org.springframework.boot").version("2.7.0")
    //id ("io.spring.dependency-management"). version ("1.0.11.RELEASE")
    id ("java")
}
apply {
    plugin("java")

}
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch:2.7.0")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    testImplementation("org.slf4j:slf4j-simple:1.7.36")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation("com.google.guava:guava:31.1-jre")
    testImplementation("org.yaml:snakeyaml:1.26")
}

description = "sidfinance"
