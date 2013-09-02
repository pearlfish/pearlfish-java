package com.natpryce.pearlfish.formats;

import com.google.common.io.Resources;
import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.samskivert.mustache.Escaping;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class TemplatedTextFormat implements Format<Object> {
    private final Template template;
    private final TextFilter postTemplateFilter;
    private final String fileExtension;
    private final FormatType fileType;

    private TemplatedTextFormat(Template template, TextFilter postTemplateFilter, String fileExtension, FormatType fileType) {
        this.template = template;
        this.postTemplateFilter = postTemplateFilter;
        this.fileExtension = fileExtension;
        this.fileType = fileType;
    }

    public static TemplatedTextFormat create(URL templateURL, Charset charset, TextFilter escaping, TextFilter postTemplateFilter, String fileExtension, FormatType fileType) {
        return new TemplatedTextFormat(loadTemplate(templateURL, charset, escaping),
                postTemplateFilter, fileExtension, fileType);
    }

    @Override
    public String fileExtension() {
        return fileExtension;
    }

    @Override
    public FormatType fileType() {
        return fileType;
    }

    @Override
    public void write(Object value, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(postTemplateFilter.filter(template.execute(value)));
        writer.flush();
    }

    private static Template loadTemplate(URL templateURL, Charset charset, TextFilter escaping)  {
        try {
            Reader r = Resources.asCharSource(templateURL, charset).openStream();
            try {
                return Mustache.compiler().escaping(asJMustacheEscaping(escaping)).compile(r);
            } finally {
                r.close();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid template " + templateURL, e);
        }
    }

    private static Escaping asJMustacheEscaping(final TextFilter escaping) {
        return new Escaping() {
            @Override
            public String escape(String raw) {
                return escaping.filter(raw);
            }
        };
    }
}
