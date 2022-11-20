
rootProject.name = "doggybeans"
include(":sidfinance")
include(":Sgt-Sid")
pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
}
