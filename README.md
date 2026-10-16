# Aircraft Wing Calculator

## Requirements

- Java 17 or newer
- Apache Maven 3.6.3 or newer

## JavaFX

JavaFX Controls is managed by Maven using the OpenJFX 17.0.14 dependency and
the platform-specific JavaFX runtime is resolved automatically. Launch the
JavaFX sample application with Maven so the runtime is on the module path:

```sh
mvn javafx:run
```

This starts `com.project.Tets`. The existing `com.project.GUI` remains a
separate console application and is still the JAR's default entry point.

## Build

From the project directory, run:

```sh
mvn package
```

Maven compiles the application and creates an executable JAR at
`target/aerocraft-design-project-1.0-SNAPSHOT.jar`.

## Run

```sh
java -jar target/aerocraft-design-project-1.0-SNAPSHOT.jar
```
