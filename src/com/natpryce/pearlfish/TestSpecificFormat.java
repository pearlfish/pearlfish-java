package com.natpryce.pearlfish;

/**
 * Required to get Javac to resolve generic type parameters correctly.
 *
 * One day I hope this can be removed and TestSpecific<Format<T>> used directly.
 *
 * But I'm not holding my breath...
 */
public interface TestSpecificFormat<T> extends TestSpecific<Format<T>> {
}
