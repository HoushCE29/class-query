package dev.houshce29.classquery;

import java.util.function.Predicate;

/**
 * Executable query model representing a "where/and/or" clause.
 */
public class Where extends ExecutableComponent {
    private final ClassQuery query;

    Where(ClassQuery query) {
        super(query);
        this.query = query;
    }

    /**
     * Applies the filter as an AND clause.
     * @param filter Filter to apply.
     * @return This.
     */
    public Where and(Predicate<Class> filter) {
        query.and(filter);
        return this;
    }
}
