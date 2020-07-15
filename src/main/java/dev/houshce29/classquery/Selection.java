package dev.houshce29.classquery;

import dev.houshce29.classquery.internal.Util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Model for selecting items from a package.
 */
public enum Selection {

    /**
     * Selects everything.
     */
    ALL(c -> true),

    /**
     * Selects classes (anything with the <pre>class</pre> keyword, or anonymous/synthetic types).
     */
    CLASSES(c -> !c.isInterface() && !c.isAnnotation() && !c.isEnum()),

    /**
     * Selects interfaces (anything with the <pre>interface</pre> keyword).
     */
    INTERFACES(c -> c.isInterface() && !c.isAnnotation()),

    /**
     * Selects annotations (anything with the <pre>@interface</pre> keyword).
     */
    ANNOTATIONS(Class::isAnnotation),

    /**
     * Selects enums (anything with the <pre>enum</pre> keyword).
     */
    ENUMS(Class::isEnum);

    private static final Set<Selection> EFFECTIVELY_ALL = Util.immutableSet(CLASSES, INTERFACES, ANNOTATIONS, ENUMS);
    private final Predicate<Class> filter;

    Selection(Predicate<Class> filter) {
        this.filter = filter;
    }

    /**
     * @return The selection as a predicate.
     */
    public Predicate<Class> toPredicate() {
        return filter;
    }

    /**
     * Reduces the selections into a single predicate.
     * @param selections Selections to reduce.
     * @return Single predicate.
     */
    public static Predicate<Class> reduce(Selection... selections) {
        return reduce(Arrays.asList(selections));
    }

    /**
     * Reduces the selections into a single predicate.
     * @param selections Selections to reduce.
     * @return Single predicate.
     */
    public static Predicate<Class> reduce(Collection<Selection> selections) {
        Set<Selection> selects = new HashSet<>(selections);
        if (selects.isEmpty() || selects.contains(ALL) || EFFECTIVELY_ALL.equals(selects)) {
            return ALL.toPredicate();
        }
        return selects.stream()
                .map(Selection::toPredicate)
                .reduce(Predicate::or)
                .get();
    }
}
