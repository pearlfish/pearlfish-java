package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;
import com.natpryce.pearlfish.internal.XmlEscaping;

import java.nio.charset.Charset;

import static com.natpryce.pearlfish.FormatType.TEXT;

/**
 * Formats that use a template and will fail if the template does not exist.
 *
 * Each of the formats in this class has an equivalent in the {@link Formats} class that falls back
 * to a safe format if a template for the test does not exist.
 *
 * The formats in this class can be configured with a specific format, allowing formats to an be shared
 * between tests.
 */
public class TemplateFormats {
    public static final TestSpecificTemplatedTextFormat PLAIN_TEXT =
            create(".txt", TEXT, TextFilter.IDENTITY, TextFilter.IDENTITY);

    public static final TestSpecificTemplatedTextFormat MARKDOWN =
            create(".md", TEXT.specialised("markdown"), new MarkdownEscaping(), new MarkdownTableLayoutFilter());

    public static final TestSpecificTemplatedTextFormat XML = xml(".xml", FormatType.XML);

    public static TestSpecificTemplatedTextFormat xml(final String fileExtension, final FormatType formatType) {
        return create(fileExtension, formatType, new XmlEscaping(), TextFilter.IDENTITY);
    }

    private static TestSpecificTemplatedTextFormat create(String fileExtension, FormatType formatType, TextFilter escaping, TextFilter postTemplateFilter) {
        return new TestSpecificTemplatedTextFormat(fileExtension, formatType, Charset.defaultCharset(), escaping, postTemplateFilter);
    }

}
