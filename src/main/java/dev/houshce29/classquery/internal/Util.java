package dev.houshce29.classquery.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}
