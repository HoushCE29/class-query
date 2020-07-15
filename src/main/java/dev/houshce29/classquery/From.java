package dev.houshce29.classquery;

import java.util.function.Predicate;

/**
 * Executable query model of the "from" definition.
 */
public class From extends ExecutableComponent {
    private final ClassQuery query;

    From(ClassQuery query) {
        super(query);
        this.query = query;
    }

    /**
     * Applies an initial where clause and returns the "where" model.
     * @param filter Filter to apply in the where clause.
     * @return Where model.
     */
    public Where where(Predicate<Class> filter) {
        query.and(filter);
        return new Where(query);
    }
}
