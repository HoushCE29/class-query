package dev.houshce29.classquery;

import dev.houshce29.classquery.engine.EngineFactory;
import dev.houshce29.classquery.testenv.Annotation;
import dev.houshce29.classquery.testenv.Enum;
import dev.houshce29.classquery.testenv.Interface;
import dev.houshce29.classquery.testenv.classes.AnnotatedClass;
import dev.houshce29.classquery.testenv.classes.AnnotatedImplClass;
import dev.houshce29.classquery.testenv.classes.Classes;
import dev.houshce29.classquery.testenv.classes.ImplClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class TU_ClassQuery {
    private static final String PACKAGE = "dev.houshce29.classquery.testenv";

    @Test
    public void testQueryAll() {
        Set<Class> result = ClassQuery.select().from(PACKAGE).execute();
        assertContainsOnly(result, Classes.class, Classes.NestedClass.class, Classes.local(),
                Classes.anonymous(), AnnotatedClass.class, AnnotatedImplClass.class, ImplClass.class,
                Annotation.class, Enum.class, Interface.class);
    }

    @Test
    public void testQueryClasses() {
        Set<Class> result = ClassQuery.select(Selection.CLASSES)
                .from(PACKAGE)
                .execute();

        assertContainsOnly(result, Classes.class, Classes.NestedClass.class, Classes.local(),
                Classes.anonymous(), AnnotatedClass.class, AnnotatedImplClass.class, ImplClass.class);
    }

    @Test
    public void testQueryInterfaces() {
        Set<Class> result = ClassQuery.select(Selection.INTERFACES)
                .from(PACKAGE)
                .execute();

        assertContainsOnly(result, Interface.class);
    }

    @Test
    public void testQueryEnums() {
        Set<Class> result = ClassQuery.select(Selection.ENUMS)
                .from(PACKAGE)
                .execute();

        assertContainsOnly(result, Enum.class);
    }

    @Test
    public void testQueryAnnotations() {
        Set<Class> result = ClassQuery.select(Selection.ANNOTATIONS)
                .from(PACKAGE)
                .execute();

        assertContainsOnly(result, Annotation.class);
    }

    @Test
    public void testQueryComposite() {
        Set<Class> result = ClassQuery.select(Selection.INTERFACES, Selection.ANNOTATIONS)
                .from(PACKAGE)
                .execute();

        assertContainsOnly(result, Interface.class, Annotation.class);
    }

    @Test
    public void testQueryWithWhereClause() {
        Set<Class> result = ClassQuery.select(Selection.CLASSES)
                .from(PACKAGE)
                .where(clazz -> clazz.getAnnotation(Annotation.class) != null)
                .and(clazz -> clazz.getInterfaces().length == 0)
                .execute();

        assertContainsOnly(result, AnnotatedClass.class);
    }

    @Test
    public void testQueryWithParallelEngine() {
        Set<Class> result = ClassQuery.select(Selection.CLASSES)
                .from(PACKAGE)
                .where(clazz -> clazz.getAnnotation(Annotation.class) != null)
                .and(clazz -> clazz.getInterfaces().length == 0)
                .execute(EngineFactory.parallelEngine(4));

        assertContainsOnly(result, AnnotatedClass.class);
    }

    private static void assertContainsOnly(Set<Class> result, Class... expected) {
        Assert.assertEquals("Expected classes differs in size.", expected.length, result.size());
        for (Class cl : expected) {
            Assert.assertTrue("Result does NOT contain: " + cl.getSimpleName(), result.contains(cl));
        }
    }
}
