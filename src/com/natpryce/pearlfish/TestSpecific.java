package com.natpryce.pearlfish;

/**
 * Provides an object configured for a single test.
 *
 * @param <T> the type of object provided
 */
public interface TestSpecific<T> {
    /**
     * Provides an object configured for a single test.
     *
     * @param testClass the class that implements the test
     * @param testName the name of the test (usually the method that implements the test).
     * @return an object configured for the test
     */
    T forTest(Class<?> testClass, String testName);
}
