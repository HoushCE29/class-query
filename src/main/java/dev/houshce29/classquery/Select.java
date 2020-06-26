package dev.houshce29.classquery;

public class Select {
    private final ClassQuery query;

    Select(ClassQuery query) {
        this.query = query;
    }

    public From from(String apackage, String... morePackages) {
        query.from(apackage);
        query.from(morePackages);
        return new From(query);
    }
}
