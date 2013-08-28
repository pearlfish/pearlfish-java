package com.natpryce.pearlfish.formats;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;
import com.samskivert.mustache.Escaping;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Provides a TemplatedTextFormat configured for a single test that can be given fallback behaviour
 * if the template does not exist..
 */
public class TestSpecificTemplatedTextFormat implements TestSpecific<TemplatedTextFormat> {
    private final String fileExtension;
    private final FormatType fileType;
    private final Charset charset;
    private final Escaping escaping;
    private final TextFilter postTemplateFilter;

    public TestSpecificTemplatedTextFormat(String fileExtension, FormatType fileType, Charset charset, Escaping escaping, TextFilter postTemplateFilter) {
        this.fileExtension = fileExtension;
        this.fileType = fileType;
        this.charset = charset;
        this.escaping = escaping;
        this.postTemplateFilter = postTemplateFilter;
    }

    @Override
    public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
        String resourceName = testClass.getSimpleName() + "." + testName + fileExtension + ".template";
        return createFormat(loadTemplate(testClass, resourceName));
    }

    public TestSpecific<TemplatedTextFormat> withTemplate(final String resourceName) {
        return new TestSpecific<TemplatedTextFormat>() {
            @Override
            public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                return createFormat(loadTemplate(testClass, resourceName));
            }
        };
    }

    public TestSpecific<TemplatedTextFormat> withTemplate(final Class<?> owningClass, final String resourceName) {
        return new TestSpecific<TemplatedTextFormat>() {
            @Override
            public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                return createFormat(loadTemplate(owningClass, resourceName));
            }
        };
    }

    public TestSpecific<TemplatedTextFormat> withTemplate(final File file) {
        return new TestSpecific<TemplatedTextFormat>() {
            @Override
            public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                return createFormat(loadTemplate(Files.asByteSource(file), file.toString()));
            }
        };
    }

    /**
     * Returns a test-specific format provider that will fall back to a given format if no template
     * exists for a test.
     *
     * @param fallbackFormat the format to use when there is no template for a test.
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

    private TemplatedTextFormat createFormat(Template template) {
        return new TemplatedTextFormat(template, postTemplateFilter, fileExtension, fileType);
    }

    private Template loadTemplate(Class<?> testClass, String templateName) {
        URL resource = testClass.getResource(templateName);
        if (resource == null) {
            throw new MissingTemplateException(templateName);
        }

        return loadTemplate(Resources.asByteSource(resource), templateName);
    }

    private Template loadTemplate(ByteSource byteSource, String templateName)  {
        try {
            Reader r = byteSource.asCharSource(charset).openStream();
            try {
                return Mustache.compiler().escaping(escaping).compile(r);
            } finally {
                r.close();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid template " + templateName, e);
        }
    }
}
