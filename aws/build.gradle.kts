plugins {
    id("org.jetbrains.gradle.terraform") version "1.4.1"
}

terraform {
    version = "1.1.4"

    sourceSets {
        main {
            planVariable("region", "eu-central-1")
        }
    }
}