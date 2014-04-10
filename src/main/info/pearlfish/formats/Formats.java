package info.pearlfish.formats;

import info.pearlfish.Format;
import info.pearlfish.FormatType;
import info.pearlfish.TestSpecific;
import info.pearlfish.internal.ToStringFormat;
import info.pearlfish.internal.YamlFormat;

import java.nio.charset.Charset;

import static info.pearlfish.FormatType.TEXT;

/**
 * Commonly used file formats.
 *
 * Templated formats defined by this class fall back to {@link #YAML} format if no template can be found.
 * If you want a templated format that will fail when no template can be found, use those defined by the
 * {@link TemplateFormats} class.
 */
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

    /**
     * Serialises objects to YAML format to a text file with the ".yaml" file extension,
     *
     * The serialised YAML is intended only for output.  It cannot be deserialised into objects again. It does
     * not contain any class names, so it is possible to rename or move classes without changing output.
     */
    public static final TestSpecific<Format<Object>> YAML = new TestSpecific<Format<Object>>() {
        @Override
        public Format<Object> forTest(Class<?> testClass, String testName) {
            return new YamlFormat(Charset.defaultCharset());
        }
    };

    /**
     * Formats values as Markdown documents using a template and writes the results to a file with the ".md"
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     */
    public static final TestSpecific<Format<Object>> MARKDOWN = safe(TemplateFormats.MARKDOWN);

    /**
     * Formats values as plain text documents using a template and writes the results to a file with the ".txt"
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     */
    public static final TestSpecific<Format<Object>> PLAIN_TEXT = safe(TemplateFormats.PLAIN_TEXT);

    /**
     * Formats values as Markdown documents using a template and writes the results to a file with the ".xml"
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     */
    public static final TestSpecific<Format<Object>> XML = safe(TemplateFormats.XML);

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
        return TemplateFormats.xml(fileExtension, formatType).ifNoTemplate(YAML);
    }

    /**
     * Formats values as XML SVG images using a template and writes the results to a file with the ".svg"
     * extension.  If there is no template, it falls back to using the {@link Formats#YAML} formatter.
     */
    public static final TestSpecific<Format<Object>> SVG = safe(TemplateFormats.SVG);

    private static TestSpecific<Format<Object>> safe(TestSpecificTemplatedTextFormat baseFormat) {
        return baseFormat.ifNoTemplate(YAML);
    }
}
