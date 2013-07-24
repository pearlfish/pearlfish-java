package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;
import com.natpryce.pearlfish.internal.YamlFormatter;
import org.rococoa.okeydoke.Formatter;

import java.nio.charset.Charset;

public interface Formats {
    Format MARKDOWN = new Format() {
        @Override
        public TemplateFormatter formatterFor(String testName, Class<?> testClass) {
            return new TemplateFormatter(testClass, testName, Charset.defaultCharset(),
                    new MarkdownEscaping(),
                    new MarkdownTableLayoutFilter());
        }

        @Override
        public String fileExtension() {
            return ".md";
        }
    };

    Format YAML = new Format() {
        @Override
        public Formatter<Object, String> formatterFor(String testName, Class<?> testClass) {
            return new YamlFormatter(Charset.defaultCharset());
        }

        @Override
        public String fileExtension() {
            return ".yaml";
        }
    };
}
