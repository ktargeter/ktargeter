# ktargeter [![build](https://github.com/ktargeter/ktargeter/workflows/build/badge.svg)](https://github.com/ktargeter/ktargeter/actions?query=workflow%3Abuild) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.ktargeter/gradle-plugin/badge.svg)](https://search.maven.org/artifact/org.ktargeter/gradle-plugin) [![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
<a href="/docs/readme.md">English</a>

<img src="./logo.svg" align="right" width="150px" alt="ktargeter logo">

Ktargeter는 properties을 위한 annotation use-site targets를 재정의할 수 있는 Kotlin 컴파일러 플러그인입니다. 
Kotlin 코드에서 Java annotation들을 사용하려면 종종 use-site targets을 지정해야 하는데, 
이는 불편할 뿐만 아니라 target이 지정되지 않았을 때 런타임 버그로 이어집니다.
Ktargeter는 여러분이 `@get:Email`, `@field:Email`, 또는 `@set:Email`을 모두 기억하는 대신에, 
Gradle에서 단 한 번만 구성하면 코드 전체에서 `@Email`을 사용할 수 있게 합니다.
Ktargeter는 컴파일 중에 작동하며, 런타임 중에 추가 오버헤드를 발생시키지 않습니다.

예를 들어, 여러분은 이 코드를:
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
이렇게 고칠 수 있겠죠:

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

## 사용
Kotlin 1.5 혹은 이후 버전(또는 Kotlin 1.4 
[with JVM IR backend enabled](https://kotlinlang.org/docs/whatsnew1430.html#jvm-ir-compiler-backend-reaches-beta)).

ktargeter를 `build.gradle`의 `plugins`부분에 추가하는 방법:
```gradle
plugins {
    id 'org.ktargeter' version '0.1.0'
}
```

새 target의 annotation을 정의하는 방법:
```gradle
ktargeter.annotations = [
        "com.sample.annotations.One"  : "get",
        "com.sample.annotations.Two"  : "field",
        "com.sample.annotations.Three": "set"
]
```

지정된 annotation들을 properties에 사용할 때, 이 annotation에 대한 use-site targets을 재정의하는 플러그인입니다.

Ktargeter는 target을 명시적으로 지정하는 annotations의 target들을 재정의하지 않습니다. 

## 기여

버그를 발견했거나, Ktargeter를 개선할 아이디어가 있으면 언제든지 issue를 통해 기여해주세요.
또는 Pull Request를 통해 변경사항을 제안할 수 있습니다.

## 개발 환경 설정

Ktargeter를 디버그 또는 개발한다면 다음의 방법을 이용해주세요:

1. Gradle 및 Kotlin 플러그인을 로컬 Maven 저장소에 설치합니다.:
```sh
./gradlew publishToMavenLocal -x signMavenPublication \
 -x signPluginMavenPublication -x signSimplePluginPluginMarkerMavenPublication
```

2. 프로젝트를 IntelliJ IDEA('Debug Kotlin Plugin' 환경*)에서 Import합니다.
    * Run/Debug Configuration 창에서 사용 가능해야 합니다.

3. 'compiler-plugin' 모듈의 클래스에 중단점을 추가합니다.

4. [the sample project](https://github.com/ktargeter/ktargeter-sample)
다음 명령을 사용하여 복제 및 빌드합니다.:
```sh
./gradlew clean build --no-daemon -Dorg.gradle.debug=true \
 -Dkotlin.compiler.execution.strategy="in-process" \
 -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
```
5. `Debug Kotlin Plugin`구성을 디버깅 모드에서 실행합니다..
