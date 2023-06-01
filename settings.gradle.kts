rootProject.name = "doggybeans"

include(":Sgt-Sid")
pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
}
