package com.natpryce.pearlfish.formats;

import com.google.common.io.Resources;
import com.samskivert.mustache.Escaping;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.rococoa.okeydoke.formatters.StringFormatter;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class TemplateFormatter extends StringFormatter {
    private final Template template;
    private final TextFilter postTemplateFilter;

    public TemplateFormatter(Class<?> testClass, String testName, Charset charset, Escaping valueEscaping, TextFilter postTemplateFilter) {
        super(charset);
        this.template = loadTemplate(testClass, testName + ".template", charset, valueEscaping);
        this.postTemplateFilter = postTemplateFilter;
    }

    @Override
    public String formatted(Object actual) {
        return postTemplateFilter.filter(template.execute(actual));
    }

    private static Template loadTemplate(Class<?> testClass, String templateName, Charset charset, Escaping valueEscaping) {
        try {
            URL resource = testClass.getResource(templateName);
            if (resource == null) {
                throw new MissingTemplateException(templateName);
            }

            return Mustache.compiler()
                    .escaping(valueEscaping)
                    .compile(Resources.toString(resource, charset));
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid template " + templateName, e);
        }
    }
}
