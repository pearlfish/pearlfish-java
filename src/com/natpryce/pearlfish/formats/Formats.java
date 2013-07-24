package com.natpryce.pearlfish.formats;

import org.rococoa.okeydoke.Formatter;

import java.nio.charset.Charset;

public interface Formats {
    Format MARKDOWN = new Format() {
        @Override
        public MarkdownFormatter formatterFor(String testName, Class<?> testClass) {
            return new MarkdownFormatter(testClass, testName, Charset.defaultCharset());
        }
    };
}
