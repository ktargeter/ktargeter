# ktargeter [![build](https://github.com/ktargeter/ktargeter/workflows/build/badge.svg)](https://github.com/ktargeter/ktargeter/actions?query=workflow%3Abuild)
Override annotation targets in your Kotlin projects

# How to develop/debug locally

1. Install Gradle plugin and Kotlin plugin into local Maven repository
```sh
./gradlew :kotlin-plugin:install :gradle-plugin:install
```

2. Import the project to IntelliJ IDEA. The `Debug Kotlin Plugin` should be
available in Run/Debug configuration box.

3. Add breakpoints in the `kotlin-plugin` module.

4. Build the sample project in Debug mode
```sh
./gradlew :sample:build --no-daemon -Dorg.gradle.debug=true -Dkotlin.compiler.execution.strategy="in-process" -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
```
5. Run the `Debug Kotlin Plugin` configuration in Debug mode.
