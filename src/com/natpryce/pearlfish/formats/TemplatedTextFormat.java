package com.natpryce.pearlfish.formats;

import com.google.common.io.Resources;
import com.natpryce.pearlfish.Format;
import com.samskivert.mustache.Escaping;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;

public class TemplatedTextFormat implements Format<Object> {
    private final Template template;
    private final TextFilter postTemplateFilter;
    private final String fileExtension;

    public TemplatedTextFormat(Template template, TextFilter postTemplateFilter, String fileExtension) {
        this.template = template;
        this.postTemplateFilter = postTemplateFilter;
        this.fileExtension = fileExtension;
    }

    public static TemplatedTextFormat fromResource(Class<?> relativeToClass, String baseName, String fileExtension, Charset charset, Escaping valueEscaping, TextFilter postTemplateFilter) {
        final Template template = loadTemplate(relativeToClass, relativeToClass.getSimpleName() + "." + baseName + fileExtension + ".template", charset, valueEscaping);
        return new TemplatedTextFormat(template, postTemplateFilter, fileExtension);

    }

    @Override
    public String extension() {
        return fileExtension;
    }

    @Override
    public void write(Object value, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(postTemplateFilter.filter(template.execute(value)));
        writer.flush();
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
