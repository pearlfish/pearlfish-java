package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;
import com.samskivert.mustache.formats.HtmlEscaping;
import com.samskivert.mustache.formats.NoEscaping;

import java.nio.charset.Charset;

import static com.natpryce.pearlfish.FormatType.TEXT;

/**
 * Formats that use a template and will fail if the template does not exist.
 *
 * Each of the formats in this class has an equivalent in the {@link Formats} class that falls back
 * to a safe format if a template does not exist.
 */
public class TemplateFormats {
    public static final TestSpecificTemplatedTextFormat MARKDOWN = new TestSpecificTemplatedTextFormat() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(),
                    new MarkdownEscaping(), new MarkdownTableLayoutFilter(),
                    ".md", TEXT.specialised("markdown"));
        }
    };

    public static final TestSpecificTemplatedTextFormat PLAIN_TEXT = new TestSpecificTemplatedTextFormat() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(),
                    new NoEscaping(), TextFilter.IDENTITY,
                    ".txt", TEXT);
        }
    };

    public static final TestSpecificTemplatedTextFormat XML = xml(".xml", FormatType.XML);

    public static TestSpecificTemplatedTextFormat xml(final String fileExtension, final FormatType formatType) {
        return new TestSpecificTemplatedTextFormat() {
            @Override
            public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(),
                        new HtmlEscaping(), TextFilter.IDENTITY,
                        fileExtension, formatType);
            }
        };
    }

}
