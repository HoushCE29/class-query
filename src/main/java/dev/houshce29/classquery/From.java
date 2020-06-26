package dev.houshce29.classquery;

import dev.houshce29.classquery.filter.Filter;

import java.util.function.Predicate;

public class From extends ExecutableClassQueryComponent {
    private final ClassQuery query;

    From(ClassQuery query) {
        super(query);
        this.query = query;
    }

    public From andFrom(String apackage, String... morePackages) {
        query.from(apackage);
        query.from(morePackages);
        return this;
    }

    public Where where(Predicate<Class> filter) {
        query.and(filter);
        return new Where(query);
    }

    public Where where(Filter filter) {
        return where(filter.toPredicate());
    }
}
