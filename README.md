# Spec reported for Spek Framework
This library allows automatically generate text files with specifications directly from your tests.

## Inspiration
I love the [Kotlin](https://kotlinlang.org/) and [Spek framework](https://www.spekframework.org/). This combination allows creating human-readable tests (please take a look into the [Kluent](https://markusamshove.github.io/Kluent/) assertion library).

I want to get "out of the box" only one thing - it's text specification files (something like in [Cucumber](https://cucumber.io/)).

## Install
### Gradle
1. Add repository
  ```groovy
    repositories {
      maven {
        url = uri("https://maven.pkg.github.com/paslavsky/spec-repo")
      }
    }
  ```
2. Add dependency
  ```groovy
  dependencies {
    testImplementation 'com.github.paslavsky:spec-report:<version>'
  }
  ```
### Maven
1. Add repository
  ```xml
  <distributionManagement>
     <repository>
       <id>spec-report</id>
       <name>Scec report repository</name>
       <url>https://maven.pkg.github.com/paslavsky/spec-report</url>
     </repository>
  </distributionManagement>
  ```
2. Add dependency
```xml
<dependencies>
  <dependency>
    <groupId>com.github.paslavsky</groupId>
    <artifactId>spec-report</artifactId>
    <version>${spec-report.version}</version>
  </dependency>
</dependencies>
```

## Use
Please create your abstract class for basic Spek tests, for example:
```kotlin
abstract class MySpek(body: Root.() -> Unit) : Spek({
    registerListener(SpecWriter)
    // ... <- your custom logic goes here
    body(this)
})
```
or you can use my class `com.github.paslavsky.Spec`.

So, your tests will look something like this:
```kotlin
object CalculatorSpec: MySpek({
    describe("A calculator") {
        val calculator by memoized { Calculator() }

        describe("addition") {
            it("returns the sum of its arguments") {
                assertEquals(3, calculator.add(1, 2))
            }
        }
    }
})
```

Now, run your tests and look into the `build/reports/specs` folder. You should find a new spec file:
```text
 A calculator
   addition
     returns the sum of its arguments
```

P.S. Please see tests for more examples.

## Configuration
The `spec-report` allows some customization. It could be done programmatically or via the `spec-report.properties` file.

### Programmatic way
You can change what you need directly at the `com.github.paslavsky.Configuration` object. For example, you can create your abstract spec class:

```kotlin
abstract class MySpek(body: Root.() -> Unit) : Spek({
    registerListener(SpecWriter)
    
    Configuration.failed = "FAIL"
    Configuration.passed = "PASS"
    
    body(this)
})
```

### Configuration file
At the configuration file, you can define the following properties:

| Property | Default | Description |
| -------- | ------- | ----------- |
| `failed` | `'F'`   | Prefix for failed tests |
| `passed` | `' '`   | Prefix for successfully passed tests. |
| `filePattern` | `{name}.spec`   | Name pattern for your specification files. At the current moment, we have only one placeholder - `{name}`. |
| `specFolder` | `./build/reports/specs` or `./specs`   | Folder name where the library will generate specification files. |

## Contribution

If this helpful library for you and you have any ideas what to add - please welcome to create a new issue or PR.