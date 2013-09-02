package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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
}
