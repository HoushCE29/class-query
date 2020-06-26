package dev.houshce29.classquery.filter;

import java.util.function.Predicate;

/**
 * A contract for well-known, common filters.
 */
public abstract class Filter {

    /**
     * Tests that the class can pass this filter.
     * @param clazz Class to test.
     * @return true if the class passes the filter.
     */
    public abstract boolean test(Class clazz);

    /**
     * Converts this filter into a predicate.
     * @return This filter in predicate form.
     */
    public final Predicate<Class> toPredicate() {
        return this::test;
    }
}
