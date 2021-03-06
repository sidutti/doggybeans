
plugins {
    id ("org.springframework.boot").version("2.7.0")
    id ("io.spring.dependency-management"). version ("1.0.11.RELEASE")
    id ("java")
}
apply {
    plugin("java")
    plugin("org.springframework.boot")
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":sidfinance"))
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch:2.7.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.7.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.7.0")
    implementation("com.typesafe.akka:akka-http_2.13:10.2.9")
    implementation("com.typesafe.akka:akka-serialization-jackson_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-actor_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-http-spray-json_2.13:10.2.9")
    implementation("com.typesafe.akka:akka-cluster_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-cluster-metrics_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-cluster-tools_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-cluster-typed_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-cluster-sharding-typed_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-slf4j_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-stream-typed_2.13:2.6.19")
    implementation("com.typesafe.akka:akka-discovery_2.13:2.6.19")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("com.lightbend.akka.management:akka-management-cluster-bootstrap_2.13:1.1.3")
    implementation("com.lightbend.akka.discovery:akka-discovery-kubernetes-api_2.13:1.1.3")
    implementation("com.lightbend.akka.management:akka-management-cluster-http_2.13:1.1.3")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:2.7.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0")
    testImplementation("io.projectreactor:reactor-test:3.4.18")
}

description = "Sgt-Sid"
