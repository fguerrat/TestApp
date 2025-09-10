pluginManagement {
    repositories {
        google ()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://maven.fabric.io/public") }
        maven { setUrl("https://repo.eclipse.org/content/repositories/paho-snapshots/") }   // added
        maven { setUrl("https://oss.sonatype.org/content/repositories/ksoap2-android-releases/") }
        flatDir {
            dirs ("libs")
        }
    }
}

rootProject.name = "TestApp"
include(":app")
 