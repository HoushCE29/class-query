package dev.houshce29.classquery.engine;

import dev.houshce29.classquery.ClassQuery;

import java.util.Set;

/**
 * Contract for a query engine.
 */
public interface Engine {

    /**
     * Tells the engine to search for classes in the query.
     * @param query Query to use to find classes.
     * @return Set of all matching classes.
     */
    Set<Class> go(ClassQuery query);
}
