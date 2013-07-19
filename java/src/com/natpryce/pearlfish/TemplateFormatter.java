package com.natpryce.pearlfish;

import com.google.common.io.Resources;
import com.natpryce.pearlfish.internal.StreamCopierThread;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.rococoa.okeydoke.formatters.StringFormatter;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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
        Template template = loadTemplate();
        return template.execute(actual);
    }

    @Override
    public void writeTo(String s, OutputStream os) throws IOException {
        try {
            formatMarkdownWithPandoc(s, os);
        } catch (InterruptedException e) {
            throw new InterruptedIOException("interrupted while piping text through pandoc");
        }
    }

    private Template loadTemplate() {
        final String templateName = testName + ".md.template";

        try {
            return Mustache.compiler()
                    .escapeHTML(false)
                    .compile(Resources.toString(
                            getResource(testClass, templateName),
                            Charset.forName("UTF8")));
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot load template " + templateName, e);
        }
    }

    private void formatMarkdownWithPandoc(String s, OutputStream os) throws IOException, InterruptedException {
        String pandocExecutable = System.getProperty("pearlfish.pandoc", "pandoc");
        ProcessBuilder processBuilder = new ProcessBuilder(pandocExecutable, "--read=markdown+pipe_tables", "--write=markdown+pipe_tables");

        Process pandoc = processBuilder.start();
        try {
            Thread outputThread = new StreamCopierThread(pandoc.getInputStream(), os);
            Thread errorThread = new StreamCopierThread(pandoc.getInputStream(), System.err);

            outputThread.start();
            errorThread.start();

            OutputStreamWriter writer = new OutputStreamWriter(pandoc.getOutputStream());
            try {
                writer.write(s);
            }
            finally {
                writer.close();
            }

            outputThread.join();
            errorThread.join();
        }
        finally {
            pandoc.waitFor();
        }
    }
}
