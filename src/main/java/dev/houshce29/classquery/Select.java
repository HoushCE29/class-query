package dev.houshce29.classquery;

public class Select {
    private final ClassQuery query;

    Select(ClassQuery query) {
        this.query = query;
    }

    public From from(String javaPackage, String... morePackages) {
        query.from(javaPackage);
        query.from(morePackages);
        return new From(query);
    }
}
