package dev.houshce29.classquery.engine;

import dev.houshce29.classquery.engine.impl.DefaultEngine;

public final class EngineFactory {
    private EngineFactory() {
    }

    public static Engine defaultEngine() {
        return new DefaultEngine(classLoader());
    }

    public static ClassLoader classLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = EngineFactory.class.getClassLoader();
        }
        return loader;
    }
}
