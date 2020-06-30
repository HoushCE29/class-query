package dev.houshce29.classquery.engine.impl;

import dev.houshce29.classquery.ClassQuery;
import dev.houshce29.classquery.engine.Engine;
import dev.houshce29.classquery.engine.extract.Extractor;
import dev.houshce29.classquery.engine.extract.SimpleExtractor;
import dev.houshce29.classquery.engine.extract.ZipBasedExtractor;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Default simple engine.
 */
public class DefaultEngine implements Engine {
    private final ClassLoader loader;
    private final List<Extractor> extractors;

    public DefaultEngine(ClassLoader loader) {
        this.loader = loader;
        this.extractors = Arrays.asList(
                new ZipBasedExtractor(loader), new SimpleExtractor(loader));
    }

    @Override
    public Set<Class> go(ClassQuery query) {
        Set<Class> classes = new HashSet<>();
        for (String javaPackage : query.getPackages()) {
            classes.addAll(tryQueryPackage(javaPackage, query.getSelect(), query.getFilters()));
        }
        return classes;
    }

    private Set<Class> tryQueryPackage(String javaPackage, Predicate<Class> select, Predicate<Class> filters) {
        try {
            return queryPackage(javaPackage, select, filters);
        }
        catch (IOException ex) {
            return Collections.emptySet();
        }
    }

    private Set<Class> queryPackage(String javaPackage, Predicate<Class> select, Predicate<Class> filters) throws IOException {
        Enumeration<URL> topLevelPackages = loader.getResources(toUrl(javaPackage));
        Set<Class> classes = new HashSet<>();
        while (topLevelPackages.hasMoreElements()) {
            queryResource(topLevelPackages.nextElement(), javaPackage)
                    .filter(select)
                    .filter(filters)
                    .forEach(classes::add);
        }
        return classes;
    }

    private Stream<Class> queryResource(URL resource, String javaPackage) {
        Set<Class> classes = new HashSet<>();
        for (Extractor extractor : extractors) {
            if (extractor.canExtract(resource)) {
                classes = extractor.extract(resource, javaPackage);
                break;
            }
        }
        return classes.stream();
    }

    private String toUrl(String javaPackage) {
        return javaPackage.replaceAll("[.]", "/");
    }
}
