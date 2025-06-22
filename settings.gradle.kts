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

rootProject.name = "Community"

include(":app")
include(":core:common")
include(":core:ui")
include(":feature:auth")
include(":feature:profile")
include(":feature:restaurants")
include(":feature:search")

include(":core:database")
include(":core:network")
include(":domain:auth")
include(":domain:restaurants")
include(":data:auth")
include(":data:restaurants")