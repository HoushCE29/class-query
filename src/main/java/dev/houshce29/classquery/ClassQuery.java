package dev.houshce29.classquery;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class ClassQuery {
    private final Set<String> packages = new HashSet<>();
    // Init the predicate with a dummy, always true filter.
    // The first actual filter added will be an "and" type.
    private Predicate<Class> filters = c -> true;

    private ClassQuery() {
    }

    public Set<String> getPackages() {
        return Collections.unmodifiableSet(packages);
    }

    public Predicate<Class> getFilters() {
        return filters;
    }

    void from(String... packages) {
        this.packages.addAll(Arrays.asList(packages));
    }

    void and(Predicate<Class> andFilter) {
        filters = filters.and(andFilter);
    }

    void or(Predicate<Class> orFilter) {
        filters = filters.or(orFilter);
    }

    public static Select select() {
        return new Select(new ClassQuery());
    }
}
