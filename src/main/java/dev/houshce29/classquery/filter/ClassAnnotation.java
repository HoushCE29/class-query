package dev.houshce29.classquery.filter;

import java.lang.annotation.Annotation;

public class ClassAnnotation extends Filter {
    private final Class<? extends Annotation> target;
    private final boolean match;

    private ClassAnnotation(Class<? extends Annotation> target, boolean match) {
        this.target = target;
        this.match = match;
    }

    public Class<? extends Annotation> getTarget() {
        return target;
    }

    public boolean shouldHaveAnnotation() {
        return match;
    }

    @Override
    public boolean test(Class clazz) {
        return (clazz.getAnnotation(target) != null) == match;
    }

    public static ClassAnnotation matching(Class<? extends Annotation> target) {
        return new ClassAnnotation(target, true);
    }

    public static ClassAnnotation notMatching(Class<? extends Annotation> avoid) {
        return new ClassAnnotation(avoid, false);
    }
}
