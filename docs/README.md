# ktargeter [![build](https://github.com/ktargeter/ktargeter/workflows/build/badge.svg)](https://github.com/ktargeter/ktargeter/actions?query=workflow%3Abuild) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.ktargeter/gradle-plugin/badge.svg)](https://search.maven.org/artifact/org.ktargeter/gradle-plugin) [![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
English | <a href="https://github.com/ktargeter/ktargeter/blob/main/docs/README_kr.md#ktargeter---">한국어</a>

<img src="./logo.svg" align="right" width="150px" alt="ktargeter logo">

Ktargeter is a Kotlin compiler plugin that allows overriding annotation use-site
targets for properties. Using Java annotations in Kotlin code often requires
specifying use-site targets, which is inconvenient and leads to bugs in runtime when
a target is not specified. Instead of memorizing whether it is `@get:Email`,
`@field:Email`, or `@set:Email`. You can configure it once in Gradle and use `@Email`
throughout your code. Ktargeter works during compilation and adds no overhead
in runtime.

As an example, you can replace this code:
```kotlin
data class User(
    @get:One
    val firstName: String,
    @field:Two
    val lastName: String,
    @set:Three
    var birthday: LocalDate,     
)
```
with this:

```kotlin
data class User(
    @One
    val firstName: String,
    @Two
    val lastName: String,
    @Three
    var birthday: LocalDate,     
)
```

## Usage
Supported Kotlin versions: 1.4, 1.5, and 1.6.

Add ktargeter to the `plugins` section of your `build.gradle`:
```gradle
plugins {
    id 'org.ktargeter' version '0.3.0'
}
```

Define annotations with new targets in the following way:
```gradle
ktargeter.annotations = [
        "com.sample.annotations.One"  : "get",
        "com.sample.annotations.Two"  : "field",
        "com.sample.annotations.Three": "set"
]
```

This will instruct the plugin to override use-site targets for the
specified annotations when they are used on properties.

Ktargeter will not override targets of annotations that specify
their targets explicitly.

## Compatibility

Pick a plugin version depending on the Kotlin version used in your project.

<table>
    <thead align="center">
        <td><b>Kotlin version</b></td>
        <td><b>Ktargeter version</b></td>
    </thead>
    <tbody>
    <tr>
        <td>Kotlin 1.6 or newer</td>
        <td>Ktargeter 0.3.0</td>
    </tr>
    <tr>
        <td>Kotlin 1.5</td>
        <td>Ktargeter 0.2.1</td>
    </tr>
    <tr>
        <td>Kotlin 1.4</td>
        <td>Ktargeter 0.1.0</td>
    </tr>
    </tbody>
</table>

## Contributing

If you found a bug or have an idea on how to improve ktargeter feel
free to open an issue. You can also propose your changes via
a Pull Request.

## Development setup

In order to debug/develop ktargeter, use the following steps:

1. Install Gradle and Kotlin plugins into the local Maven repository:
```sh
./gradlew publishToMavenLocal -x signMavenPublication \
 -x signPluginMavenPublication -x signSimplePluginPluginMarkerMavenPublication
```

2. Import the project to IntelliJ IDEA (the `Debug Kotlin Plugin` configuration
should be available in Run/Debug configuration box).

3. Add breakpoints to the classes in the `compiler-plugin` module.

4. Clone [the sample project](https://github.com/ktargeter/ktargeter-sample)
and build it using the following command:
```sh
./gradlew clean build --no-daemon -Dorg.gradle.debug=true \
 -Dkotlin.compiler.execution.strategy="in-process" \
 -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
```
5. Run the `Debug Kotlin Plugin` configuration in Debug mode.
