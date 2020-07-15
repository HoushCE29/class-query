package dev.houshce29.classquery.testenv.classes;

import dev.houshce29.classquery.testenv.Annotation;
import dev.houshce29.classquery.testenv.Interface;

@Annotation
public class AnnotatedImplClass implements Interface {
    @Override
    public String convert(Integer in) {
        throw new UnsupportedOperationException("convert");
    }
}
