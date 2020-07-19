package dev.houshce29.classquery.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as being experimental. Experimental means
 * that the class may be unstable, widely untested, or subject
 * to great change to either API or implementation.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Experimental {
}
