package com.natpryce.pearlfish;

public interface TestSpecific<T> {
    T forTest(Class<?> testClass, String testName);
}
