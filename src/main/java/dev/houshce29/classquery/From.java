package dev.houshce29.classquery;

import java.util.function.Predicate;

public class From extends ExecutableComponent {
    private final ClassQuery query;

    From(ClassQuery query) {
        super(query);
        this.query = query;
    }

    public Where where(Predicate<Class> filter) {
        query.and(filter);
        return new Where(query);
    }
}
