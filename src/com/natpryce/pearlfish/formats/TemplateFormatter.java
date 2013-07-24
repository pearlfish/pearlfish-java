package com.natpryce.pearlfish.formats;

import com.google.common.io.Resources;
import com.samskivert.mustache.Escaping;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.rococoa.okeydoke.formatters.StringFormatter;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.google.common.io.Resources.getResource;

public class TemplateFormatter extends StringFormatter {
    private final Template template;
    private final TextFilter postTemplateFilter;

    public TemplateFormatter(Class<?> testClass, String testName, Charset charset, Escaping valueEscaping, TextFilter postTemplateFilter) {
        super(charset);
        this.template = loadTemplate(testClass, testName + ".template", valueEscaping);
        this.postTemplateFilter = postTemplateFilter;
    }

    @Override
    public String formatted(Object actual) {
        return postTemplateFilter.filter(template.execute(actual));
    }

    private static Template loadTemplate(Class<?> testClass, String templateName, Escaping valueEscaping) {
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
}
