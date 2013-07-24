package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.internal.MarkdownEscaping;
import com.natpryce.pearlfish.internal.MarkdownTableLayoutFilter;
import com.natpryce.pearlfish.internal.TemplateFormat;
import com.natpryce.pearlfish.internal.YamlFormatter;
import com.samskivert.mustache.formats.NoEscaping;
import org.rococoa.okeydoke.Formatter;

import java.nio.charset.Charset;

public interface Formats {
    Format MARKDOWN = new TemplateFormat(".md", new MarkdownEscaping(), new MarkdownTableLayoutFilter());

    Format PLAIN_TEXT = new TemplateFormat(".txt", new NoEscaping(), TextFilter.IDENTITY);

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
