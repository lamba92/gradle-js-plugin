val npm by configurations.creating {
    isCanBeResolved = true
    attributes {
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named("npm-tarball"))
        attribute(Usage.USAGE_ATTRIBUTE, objects.named("js"))
    }
}

repositories {
    maven("http://localhost:8080") {
        isAllowInsecureProtocol = true
    }
}

dependencies {
    npm("npm:@spatial/turf:1.0.5")
}

tasks {
    register<Sync>("banana") {
        from(npm)
        into("$buildDir/npm")
        duplicatesStrategy = DuplicatesStrategy.WARN
    }
}