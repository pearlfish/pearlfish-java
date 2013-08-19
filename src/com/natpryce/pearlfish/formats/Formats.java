package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.TestSpecificFormat;
import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;
import com.natpryce.pearlfish.internal.YamlFormatter;
import com.samskivert.mustache.formats.NoEscaping;

import java.nio.charset.Charset;

public class Formats {
    public static TestSpecificFormat<String> string(final String fileExtension) {
        return new TestSpecificFormat<String>() {
            @Override
            public Format<String> forTest(Class<?> testClass, String testName) {
                return new StringIdentityFormat(fileExtension);
            }
        };
    }

    public static final TestSpecificFormat<String> STRING = string(".txt");

    public static final TestSpecificFormat<Object> YAML = new TestSpecificFormat<Object>() {
        @Override
        public Format<Object> forTest(Class<?> testClass, String testName) {
            return new YamlFormatter(Charset.defaultCharset());
        }
    };

    public static final TestSpecific<TemplatedTextFormat> _MARKDOWN = new TestSpecific<TemplatedTextFormat>() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, ".md", Charset.defaultCharset(), new MarkdownEscaping(), new MarkdownTableLayoutFilter());
        }
    };

    public static final TestSpecific<TemplatedTextFormat> _PLAIN_TEXT = new TestSpecific<TemplatedTextFormat>() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, ".txt", Charset.defaultCharset(), new NoEscaping(), TextFilter.IDENTITY);
        }
    };

    public static final TestSpecificFormat<Object> MARKDOWN = fallingBackTo(YAML, _MARKDOWN);

    public static final TestSpecificFormat<Object> PLAIN_TEXT = fallingBackTo(YAML, _PLAIN_TEXT);

    public static <T> TestSpecificFormat<Object> fallingBackTo(final TestSpecificFormat<Object> fallbackFormat, final TestSpecific<TemplatedTextFormat> templatedFormat) {
        return new TestSpecificFormat<Object>() {
            @Override
            public Format<Object> forTest(Class<?> testClass, String testName) {
                try {
                    return templatedFormat.forTest(testClass, testName);
                } catch (MissingTemplateException e) {
                    return fallbackFormat.forTest(testClass, testName);
                }
            }
        };
    }
}
