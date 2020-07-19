package dev.houshce29.classquery.engine;

import dev.houshce29.classquery.engine.impl.DefaultEngine;
import dev.houshce29.classquery.engine.impl.Extractor;
import dev.houshce29.classquery.engine.impl.StandardClassExtractor;
import dev.houshce29.classquery.engine.impl.ZippedClassExtractor;
import dev.houshce29.classquery.engine.impl.async.ParallelEngine;

import java.util.Arrays;
import java.util.List;

/**
 * Factory for anything pertaining to engines.
 */
public final class EngineFactory {
    private EngineFactory() {
    }

    /**
     * @return A default query engine instance.
     */
    public static Engine defaultEngine() {
        return new DefaultEngine(classLoader());
    }

    /**
     * Returns a parallel engine instance using the given
     * max allowed threads as a thread allocation threshold.
     * @param maxAllowedThreads Max number of threads allowed to be allocated.
     * @return New parallel engine instance.
     */
    public static Engine parallelEngine(int maxAllowedThreads) {
        return new ParallelEngine(maxAllowedThreads);
    }

    /**
     * @return A class loader instance which can be used to obtain resources.
     */
    public static ClassLoader classLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = EngineFactory.class.getClassLoader();
        }
        return loader;
    }

    /**
     * Returns all extractors extracting with the given class loader.
     * @param loader Class loader to extract with.
     * @return List of all extractors.
     */
    public static List<Extractor> extractors(ClassLoader loader) {
        return Arrays.asList(
                new ZippedClassExtractor(loader), new StandardClassExtractor(loader));
    }

    /**
     * Returns all extractors extracting with this thread's class loader.
     * @return List of all extractors.
     */
    public static List<Extractor> extractors() {
        return extractors(classLoader());
    }
}
