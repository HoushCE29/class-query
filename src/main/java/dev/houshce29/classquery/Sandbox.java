package dev.houshce29.classquery;

public class Sandbox {

    public static void main(String... args) {
        ClassQuery.select(Selection.ANNOTATIONS)
                .from("com.houshce29")
                .executeToStream()
                .forEach(System.out::println);
    }
}
