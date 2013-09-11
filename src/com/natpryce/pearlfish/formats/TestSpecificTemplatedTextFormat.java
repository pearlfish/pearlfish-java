package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Provides a TemplatedTextFormat configured for a single test that can be given fallback behaviour
 * if the template does not exist..
 */
@SuppressWarnings("UnusedDeclaration")
public class TestSpecificTemplatedTextFormat implements TestSpecific<TemplatedTextFormat> {
    private final String fileExtension;
    private final FormatType fileType;
    private final Charset charset;
    private final TextFilter escaping;
    private final TextFilter postTemplateFilter;

    public TestSpecificTemplatedTextFormat(String fileExtension, FormatType fileType, Charset charset, TextFilter escaping, TextFilter postTemplateFilter) {
        this.fileExtension = fileExtension;
        this.fileType = fileType;
        this.charset = charset;
        this.escaping = escaping;
        this.postTemplateFilter = postTemplateFilter;
    }

    @Override
    public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
        final String resourceName = testClass.getSimpleName() + "." + testName + fileExtension + ".template";
        return createFormat(testClass, resourceName);
    }

    public TestSpecific<TemplatedTextFormat> withTemplate(final String resourceName) {
        return new TestSpecific<TemplatedTextFormat>() {
            @Override
            public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                return createFormat(testClass, resourceName);
            }
        };
    }

    public TestSpecific<TemplatedTextFormat> withTemplate(final Class<?> owningClass, final String resourceName) {
        return new TestSpecific<TemplatedTextFormat>() {
            @Override
            public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                return createFormat(owningClass, resourceName);
            }
        };
    }

    public TestSpecific<TemplatedTextFormat> withTemplateFile(String path) {
        return withTemplate(new File(path));
    }

    public TestSpecific<TemplatedTextFormat> withTemplate(final File file) {
        try {
            final URL templateURL = file.toURI().toURL();
            return new TestSpecific<TemplatedTextFormat>() {
                @Override
                public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
                    return createFormat(templateURL);
                }
            };
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("cannot get URL for " + file);
        }
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
        final TestSpecificTemplatedTextFormat preferredFormat = this;

        return new TestSpecific<Format<Object>>() {
            @Override
            public Format<Object> forTest(Class<?> testClass, String testName) {
                try {
                    return preferredFormat.forTest(testClass, testName);
                } catch (MissingTemplateException e) {
                    return fallbackFormat.forTest(testClass, testName);
                }
            }
        };
    }

    private TemplatedTextFormat createFormat(Class<?> c, String resourceName) {
        return createFormat(urlForResource(c, resourceName));
    }

    private URL urlForResource(Class<?> testClass, String templateName) {
        URL resource = testClass.getResource(templateName);
        if (resource == null) {
            throw new MissingTemplateException(templateName);
        }
        return resource;
    }

    private TemplatedTextFormat createFormat(URL templateURL) {
        return TemplatedTextFormat.create(templateURL, charset, escaping, postTemplateFilter, fileExtension, fileType);
    }
}
