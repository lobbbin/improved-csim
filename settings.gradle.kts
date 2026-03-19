pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CascadeSim"

include(":app")
include(":core")
include(":feature:elections")
include(":feature:economy")
include(":feature:law")
include(":feature:ministries")
include(":feature:infrastructure")
include(":feature:diplomacy")
include(":feature:demographics")
include(":feature:sports")
