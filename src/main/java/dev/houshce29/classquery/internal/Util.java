package dev.houshce29.classquery.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Don't include third-party software! So stick our simple utils here!
 */
public final class Util {
    private Util() {
    }

    public static <T> List<T> toList(T[] array) {
        if (array == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(array);
    }

    public static <T> Set<T> immutableSet(T... array) {
        return Collections.unmodifiableSet(new HashSet<>(toList(array)));
    }
}
