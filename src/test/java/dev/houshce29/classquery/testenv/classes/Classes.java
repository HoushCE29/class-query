package dev.houshce29.classquery.testenv.classes;

import java.util.function.Function;

public class Classes {

    public static class NestedClass { }

    public static Class local() {
        class LocalClass { }
        return LocalClass.class;
    }

    public static Class anonymous() {
        return new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.valueOf(integer);
            }
        }.getClass();
    }

    public static Class synthetic() {
        Function<Integer, String> func = String::valueOf;
        return func.getClass();
    }
}
