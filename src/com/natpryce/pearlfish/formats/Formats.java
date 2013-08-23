package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;
import com.natpryce.pearlfish.internal.ToStringFormat;
import com.natpryce.pearlfish.internal.YamlFormat;
import com.samskivert.mustache.formats.HtmlEscaping;
import com.samskivert.mustache.formats.NoEscaping;

import java.nio.charset.Charset;

import static com.natpryce.pearlfish.FormatType.TEXT;

/**
 * Commonly used file formats.
 */
@SuppressWarnings("UnusedDeclaration")
public class Formats {
    /**
     * Formats values by calling their {@link Object#toString()} method and writes the results to a text file.
     *
     * @param fileExtension the file extension to use for the file
     * @param formatType the type of the formatted data
     * @return a text formatter
     */
    public static TestSpecific<Format<Object>> string(final String fileExtension, final FormatType formatType) {
        return new TestSpecific<Format<Object>>() {
            @Override
            public Format<Object> forTest(Class<?> testClass, String testName) {
                return new ToStringFormat(fileExtension, formatType);
            }
        };
    }

    /**
     * Formats values by calling their {@link Object#toString()} method and writes the results as a
     * plain text file with the ".txt" file extension,
     */
    public static final TestSpecific<Format<Object>> STRING = string(".txt", TEXT);

    public static final TestSpecific<Format<Object>> YAML = new TestSpecific<Format<Object>>() {
        @Override
        public Format<Object> forTest(Class<?> testClass, String testName) {
            return new YamlFormat(Charset.defaultCharset());
        }
    };

    private static final TestSpecific<TemplatedTextFormat> _MARKDOWN = new TestSpecific<TemplatedTextFormat>() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(),
                    new MarkdownEscaping(), new MarkdownTableLayoutFilter(),
                    ".md", TEXT.specialised("markdown"));
        }
    };

    private static final TestSpecific<TemplatedTextFormat> _PLAIN_TEXT = new TestSpecific<TemplatedTextFormat>() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(),
                    new NoEscaping(), TextFilter.IDENTITY,
                    ".txt", TEXT);
        }
    };

    private static final TestSpecific<TemplatedTextFormat> _XML = _xml(".xml", FormatType.XML);

    public static TestSpecific<TemplatedTextFormat> _xml(final String fileExtension, final FormatType formatType) {
        return new TestSpecific<TemplatedTextFormat>() {
            @Override
            public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(),
                        new HtmlEscaping(), TextFilter.IDENTITY,
                        fileExtension, formatType);
            }
        };
    }

    /**
     * Formats values as Markdown documents using a template and writes the results to a file with the ".md"
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     */
    public static final TestSpecific<Format<Object>> MARKDOWN = fallingBackTo(YAML, _MARKDOWN);

    /**
     * Formats values as plain text documents using a template and writes the results to a file with the ".txt"
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     */
    public static final TestSpecific<Format<Object>> PLAIN_TEXT = fallingBackTo(YAML, _PLAIN_TEXT);

    /**
     * Formats values as Markdown documents using a template and writes the results to a file with the ".xml"
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     */
    public static final TestSpecific<Format<Object>> XML = fallingBackTo(YAML, _XML);

    /**
     * Formats values as XML documents using a template and writes the results to a file with the given
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     *
     *
     * @param fileExtension the file extension to use for the file
     * @param formatType the type of the formatted data
     * @return an XML formatter
     */
    public static TestSpecific<Format<Object>> xml(String fileExtension, FormatType formatType) {
        return fallingBackTo(YAML, _xml(fileExtension, formatType));
    }

    private static TestSpecific<Format<Object>> fallingBackTo(final TestSpecific<Format<Object>> fallbackFormat, final TestSpecific<TemplatedTextFormat> templateFormat) {
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
