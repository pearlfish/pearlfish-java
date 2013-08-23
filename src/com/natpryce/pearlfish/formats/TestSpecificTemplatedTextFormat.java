package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.TestSpecific;

/**
 * Provides a TemplatedTextFormat configured for a single test that can be given fallback behaviour
 * if the template does not exist..
 */
public abstract class TestSpecificTemplatedTextFormat implements TestSpecific<TemplatedTextFormat> {
    /**
     * Returns a test-specific format provider that will fall back to a given format if no template
     * exists for a test.
     *
     * @param fallbackFormat the format to use when there is no template for a test.
     *
     * @return a test-specific format provider that will fall back to a given format if no template
     *         exists for a test.
     */
    public TestSpecific<Format<Object>> ifNoTemplate(final TestSpecific<Format<Object>> fallbackFormat) {
        final TestSpecificTemplatedTextFormat templateFormat = this;

        return new TestSpecific<Format<Object>>() {
            @Override
            public Format<Object> forTest(Class<?> testClass, String testName) {
                try {
                    return templateFormat.forTest(testClass, testName);
                } catch (MissingTemplateException e) {
                    return fallbackFormat.forTest(testClass, testName);
                }
            }
        };
    }
}
