package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.formats.Format;
import com.natpryce.pearlfish.formats.TemplateFormatter;
import com.natpryce.pearlfish.formats.TextFilter;
import com.samskivert.mustache.Escaping;
import org.rococoa.okeydoke.Formatter;

import java.nio.charset.Charset;

public class TemplateFormat implements Format {
    private final String fileExtension;
    private final Escaping escaping;
    private final TextFilter postExpansionFilter;

    public TemplateFormat(String fileExtension, Escaping escaping, TextFilter postExpansionFilter) {
        this.fileExtension = fileExtension;
        this.escaping = escaping;
        this.postExpansionFilter = postExpansionFilter;
    }

    @Override
    public Formatter<Object, String> formatterFor(String testName, Class<?> testClass) {
        return new TemplateFormatter(testName, testClass, Charset.defaultCharset(), escaping, postExpansionFilter);
    }

    @Override
    public String fileExtension() {
        return fileExtension;
    }
}
