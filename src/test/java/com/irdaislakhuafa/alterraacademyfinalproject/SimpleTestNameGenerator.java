package com.irdaislakhuafa.alterraacademyfinalproject;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

public class SimpleTestNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(Class<?> arg0) {
        return "Testing for class -> " + arg0.getSimpleName();
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> arg0, Method arg1) {
        return "Test on class -> " + arg0.getSimpleName() + " => " + arg1.getName();
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> arg0) {
        return null;
    }

}
