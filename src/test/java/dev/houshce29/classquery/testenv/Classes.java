package dev.houshce29.classquery.testenv;

import java.util.function.Function;

public class Classes {

    public static class NestedClass{ }

    public static void local() {
        class LocalClass { }
    }

    public static void anonymous() {
        new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.valueOf(integer);
            }
        };
    }

    public static void synthetic() {
        Function<Integer, String> func = String::valueOf;
    }
}
