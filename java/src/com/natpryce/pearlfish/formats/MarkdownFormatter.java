package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;

import java.nio.charset.Charset;

public class MarkdownFormatter extends TemplateFormatter {
    public MarkdownFormatter(Class<?> testClass, String testName, Charset charset) {
        super(testClass, testName, charset,
                new MarkdownEscaping(),
                new MarkdownTableLayoutFilter());
    }

    protected String fileExtension() {
        return ".md";
    }
}
