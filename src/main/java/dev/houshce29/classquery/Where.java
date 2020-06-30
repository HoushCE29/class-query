package dev.houshce29.classquery;

import java.util.function.Predicate;

public class Where extends ExecutableComponent {
    private final ClassQuery query;

    Where(ClassQuery query) {
        super(query);
        this.query = query;
    }

    public Where and(Predicate<Class> filter) {
        query.and(filter);
        return this;
    }

    public Where or(Predicate<Class> filter) {
        query.or(filter);
        return this;
    }
}
