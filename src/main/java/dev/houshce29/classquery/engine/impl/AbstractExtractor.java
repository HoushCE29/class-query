package dev.houshce29.classquery.engine.impl;

import java.util.Optional;

/**
 * Abstraction that contains some logic for resolving classes.
 */
abstract class AbstractExtractor implements Extractor {
    private final ClassLoader loader;

    AbstractExtractor(ClassLoader loader) {
        this.loader = loader;
    }

    Optional<Class> resolve(String fullyQualifiedName) {
        try {
            return Optional.ofNullable(
                    Class.forName(fullyQualifiedName, false, loader));
        }
        catch (ClassNotFoundException ex) {
            return Optional.empty();
        }
    }
}
