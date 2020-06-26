package dev.houshce29.classquery;

import dev.houshce29.classquery.filter.ClassAnnotation;
import dev.houshce29.classquery.internal.VisibleForTesting;

public class Sandbox {

    public static void main(String... args) {
        ClassQuery.select()
                .from("com.houshce29")
                .where(ClassAnnotation.notMatching(VisibleForTesting.class))
                .executeToStream()
                .forEach(System.out::println);
    }
}
