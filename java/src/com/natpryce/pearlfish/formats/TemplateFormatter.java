package com.natpryce.pearlfish.formats;

import com.google.common.io.Resources;
import com.natpryce.pearlfish.internal.TextFilter;
import com.samskivert.mustache.Escaping;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.rococoa.okeydoke.formatters.StringFormatter;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.google.common.io.Resources.getResource;

public abstract class TemplateFormatter extends StringFormatter {
    private final Class<?> testClass;
    private final String testName;
    private final Escaping valueEscaping;
    private final TextFilter postTemplateFilter;

    public TemplateFormatter(Class<?> testClass, String testName, Charset charset, Escaping valueEscaping, TextFilter postTemplateFilter) {
        super(charset);
        this.testClass = testClass;
        this.testName = testName;
        this.valueEscaping = valueEscaping;
        this.postTemplateFilter = postTemplateFilter;
    }

    @Override
    public String formatted(Object actual) {
        Template template = loadTemplate();
        return postTemplateFilter.filter(template.execute(actual));
    }

    private Template loadTemplate() {
        final String templateName = testName + fileExtension() + ".template";

        try {
            return Mustache.compiler()
                    .escaping(valueEscaping)
                    .compile(Resources.toString(
                            getResource(testClass, templateName),
                            Charset.forName("UTF8")));
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot load template " + templateName, e);
        }
    }

    protected abstract String fileExtension();
}
