# Class Query
A runtime framework to query for classes.

## Building
To build this project, simply run:
```
gradlew clean build
```

## Example
The general set-up of this library is to look similar to SQL while maintaining Java 8+ capabilities:
```java
Set<Class> result = ClassQuery.select(Selection.ENUMS, Selection.CLASSES)
    .from("dev.houshce29.test1", "dev.houshce29.test2")
    .where(c -> !"FooBarBaz".equals(c.getSimpleName()))
    .and(c -> c.getAnnotation(MyAnnotation.class) != null)
    .execute();
```