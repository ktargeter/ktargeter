# ktargeter [![build](https://github.com/ktargeter/ktargeter/workflows/build/badge.svg)](https://github.com/ktargeter/ktargeter/actions?query=workflow%3Abuild) [![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

<img src="logo.svg" align="right" width="200px" alt="ktargeter logo">

Using Java annotations in Kotlin code often requires specifying
[use-site targets](https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets).
Because there might be multiple Java elements which are generated from the corresponding Kotlin element,
you have to memorize which usee-site target is used for a particular annotation. Ktargeter allows overriding
[use-site targets](https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets)
to make code as simple as possible. You don't have remember whether it is `@get:Email`, `@field:Email`,
or `@set:Email`. You can configure it in Gradle and use simply `@Email` throughout your code. 

## How to develop/debug locally

1. Install Gradle and Kotlin plugins into the local Maven repository
```sh
./gradlew :kotlin-plugin:install :gradle-plugin:install
```

2. Import the project to IntelliJ IDEA (the `Debug Kotlin Plugin` configuration
should be available in Run/Debug configuration box).

3. Add breakpoints in the `kotlin-plugin` module.

4. Build [the sample project](https://github.com/ktargeter/ktargeter-sample)
with the following command:
```sh
./gradlew clean build --no-daemon -Dorg.gradle.debug=true \
  -Dkotlin.compiler.execution.strategy="in-process" \
  -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
```
5. Run the `Debug Kotlin Plugin` configuration in Debug mode.
