# ktargeter [![build](https://github.com/ktargeter/ktargeter/workflows/build/badge.svg)](https://github.com/ktargeter/ktargeter/actions?query=workflow%3Abuild) [![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

<img src="logo.svg" align="right" width="150px" alt="ktargeter logo">

Using Java annotations in Kotlin code often requires specifying
[use-site targets](https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets).
Because there might be multiple Java elements which are generated from the corresponding Kotlin element,
you have to memorize which use-site target is used for a particular annotation. Ktargeter allows overriding
them during compilation to prevent mistakes and make code as simple as possible. You don't have to remember
whether it is `@get:Email`, `@field:Email`, or `@set:Email`. You can configure it in Gradle and use `@Email`
throughout your code. Ktargeter works during compilation and adds no overhead in runtime.

Instead of defining annotations like this:
```kotlin
data class User(
    @get:AnnotationOne
    val firstName: String,
    @field:AnnotationTwo
    val lastName: String,
    @set:AnnotationThree
    var birthday: LocalDate,     
)
```
with ktargeter, you can define them like this:

```kotlin
data class User(
    @AnnotationOne
    val firstName: String,
    @AnnotationTwo
    val lastName: String,
    @AnnotationThree
    var birthday: LocalDate,     
)
```

## Usage
Add ktargeter to the `plugins` section of `build.gradle`:
```gradle
plugins {
    id 'org.ktargeter' version '0.1.0'
}
```
Enable IR compilation (IR will be a default in Kotlin 1.5, it is in beta in Kotlin 1.4):
``` 
compileKotlin {
    kotlinOptions.useIR = true
}
```

Define annotations with new targets in the following way:
```
ktargeter {
    enabled = true
    annotations = [
            "com.sample.AnnotationOne"  : "get",
            "com.sample.AnnotationTwo"  : "field",
            "com.sample.AnnotationThree": "set"
    ]
}
```

This will instruct the plugin to override use-site targets for the
specified annotations when they are used on properties.

Ktargeter will not override targets of annotations that specify
their targets explicitly. 

## Contributing

If you found a bug or have an idea on how to improve ktargeter
free to open an issue. You can also propose your changes via
a Pull Request.

## Development setup

In order to debug/develop ktargeter, use the following steps:

1. Install Gradle and Kotlin plugins into the local Maven repository:
```sh
./gradlew publishToMavenLocal
```

2. Import the project to IntelliJ IDEA (the `Debug Kotlin Plugin` configuration
should be available in Run/Debug configuration box).

3. Add breakpoints to the classes in the `kotlin-plugin` module.

4. Clone and build [the sample project](https://github.com/ktargeter/ktargeter-sample)
with the following command:
```sh
./gradlew clean build --no-daemon -Dorg.gradle.debug=true \
  -Dkotlin.compiler.execution.strategy="in-process" \
  -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
```
5. Run the `Debug Kotlin Plugin` configuration in Debug mode.
