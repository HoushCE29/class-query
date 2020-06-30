package dev.houshce29.classquery;

import dev.houshce29.classquery.engine.Engine;
import dev.houshce29.classquery.engine.EngineFactory;

import java.util.Set;
import java.util.stream.Stream;

/**
 * Common abstraction to fire a query at any point.
 */
public abstract class ExecutableComponent {
    private final ClassQuery query;

    ExecutableComponent(ClassQuery query) {
        this.query = query;
    }

    /**
     * Executes the class query using the given engine.
     * @param engine Engine to run the query in.
     * @return Set of matching classes.
     */
    public final Set<Class> execute(Engine engine) {
        return engine.go(query);
    }

    /**
     * Executes the class query using the default engine.
     * @return Set of matching classes.
     */
    public final Set<Class> execute() {
        return execute(EngineFactory.defaultEngine());
    }

    /**
     * Executes the class query using the given engine.
     * @param engine Engine to run the query in.
     * @return Stream of matching classes.
     */
    public final Stream<Class> executeToStream(Engine engine) {
        return execute(engine).stream();
    }

    /**
     * Executes the class query using the default engine.
     * @return Stream of matching classes.
     */
    public Stream<Class> executeToStream() {
        return executeToStream(EngineFactory.defaultEngine());
    }
}
