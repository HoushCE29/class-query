package dev.houshce29.classquery;

import dev.houshce29.classquery.filter.Filter;

import java.util.function.Predicate;

public class Where extends ExecutableClassQueryComponent {
    private final ClassQuery query;

    Where(ClassQuery query) {
        super(query);
        this.query = query;
    }

    public Where and(Predicate<Class> filter) {
        query.and(filter);
        return this;
    }

    public Where and(Filter filter) {
        return and(filter.toPredicate());
    }

    public Where or(Predicate<Class> filter) {
        query.or(filter);
        return this;
    }

    public Where or(Filter filter) {
        return or(filter.toPredicate());
    }
}
