package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;
import com.natpryce.pearlfish.internal.StringIdentityFormat;
import com.natpryce.pearlfish.internal.YamlFormat;
import com.samskivert.mustache.formats.NoEscaping;

import java.nio.charset.Charset;

public class Formats {
    public static TestSpecific<Format<String>> string(final String fileExtension, final FormatType fileType) {
        return new TestSpecific<Format<String>>() {
            @Override
            public Format<String> forTest(Class<?> testClass, String testName) {
                return new StringIdentityFormat(fileExtension, fileType);
            }
        };
    }

    public static final TestSpecific<Format<String>> STRING = string(".txt", FormatType.of("text"));

    public static final TestSpecific<Format<Object>> YAML = new TestSpecific<Format<Object>>() {
        @Override
        public Format<Object> forTest(Class<?> testClass, String testName) {
            return new YamlFormat(Charset.defaultCharset());
        }
    };

    public static final TestSpecific<TemplatedTextFormat> _MARKDOWN = new TestSpecific<TemplatedTextFormat>() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(), new MarkdownEscaping(), new MarkdownTableLayoutFilter(), ".md", FormatType.of("text", "markdown"));
        }
    };

    public static final TestSpecific<TemplatedTextFormat> _PLAIN_TEXT = new TestSpecific<TemplatedTextFormat>() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(), new NoEscaping(), TextFilter.IDENTITY, ".txt", FormatType.of("text"));
        }
    };

    public static final TestSpecific<Format<Object>> MARKDOWN = fallingBackTo(YAML, _MARKDOWN);

    public static final TestSpecific<Format<Object>> PLAIN_TEXT = fallingBackTo(YAML, _PLAIN_TEXT);

    public static TestSpecific<Format<Object>> fallingBackTo(final TestSpecific<Format<Object>> fallbackFormat, final TestSpecific<TemplatedTextFormat> templateFormat) {
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
