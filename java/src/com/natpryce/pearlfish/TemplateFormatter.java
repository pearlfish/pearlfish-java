package com.natpryce.pearlfish;

import com.google.common.io.Resources;
import org.rococoa.okeydoke.formatters.StringFormatter;
import org.stringtemplate.v4.ST;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.google.common.io.Resources.getResource;

public class TemplateFormatter extends StringFormatter {
    private final Class<?> testClass;
    private final String testName;

    public TemplateFormatter(Class<?> testClass, String testName, Charset charset) {
        super(charset);
        this.testClass = testClass;
        this.testName = testName;
    }

    @Override
    public String formatted(Object actual) {
        ST st = loadTemplate();
        st.add("results", actual);
        return st.render();
    }

    private ST loadTemplate() {
        final String templateName = testName + ".md.template";

        try {
            return new ST(Resources.toString(getResource(testClass, templateName), Charset.forName("UTF8")));
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot load template " + templateName, e);
        }
    }
}
