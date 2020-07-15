package dev.houshce29.classquery.testenv.classes;

import dev.houshce29.classquery.testenv.Interface;

public class ImplClass implements Interface {
    @Override
    public String convert(Integer in) {
        return String.valueOf(in);
    }
}
