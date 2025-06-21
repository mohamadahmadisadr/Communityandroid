pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
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

rootProject.name = "Community-android"

// Main app module
include ':app'

// Core modules
include ':core:common'
include ':core:ui'
include ':core:network'
include ':core:database'

// Data layer modules
include ':data:auth'
include ':data:restaurants'
include ':data:cafes'
include ':data:rentals'
include ':data:jobs'
include ':data:services'
include ':data:events'
include ':data:user'

// Domain layer modules
include ':domain:auth'
include ':domain:restaurants'
include ':domain:cafes'
include ':domain:rentals'
include ':domain:jobs'
include ':domain:services'
include ':domain:events'
include ':domain:user'

// Feature modules
include ':feature:auth'
include ':feature:home'
include ':feature:restaurants'
include ':feature:cafes'
include ':feature:rentals'
include ':feature:jobs'
include ':feature:services'
include ':feature:events'
include ':feature:profile'
include ':feature:search'
include ':feature:favorites'
include ':feature:messaging'