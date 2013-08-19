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
import java.net.URL;
import java.nio.charset.Charset;

public class TemplatedTextFormat implements Format<Object> {
    private final Template template;
    private final TextFilter postTemplateFilter;
    private final String fileExtension;
    private final FormatType fileType;

    public TemplatedTextFormat(Template template, TextFilter postTemplateFilter, String fileExtension, FormatType fileType) {
        this.template = template;
        this.postTemplateFilter = postTemplateFilter;
        this.fileExtension = fileExtension;
        this.fileType = fileType;
    }

    public static TemplatedTextFormat fromResource(Class<?> relativeToClass, String baseName, Charset charset, Escaping valueEscaping, TextFilter postTemplateFilter, String fileExtension, final FormatType fileType) {
        final String resourceName = relativeToClass.getSimpleName() + "." + baseName + fileExtension + ".template";

        return new TemplatedTextFormat(
                loadTemplate(relativeToClass, resourceName, charset, valueEscaping),
                postTemplateFilter,
                fileExtension,
                fileType);

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
