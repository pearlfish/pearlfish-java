package info.pearlfish.formats;

import com.google.common.io.Resources;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import info.pearlfish.Format;
import info.pearlfish.FormatType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * A format that uses a <a href="http://mustache.github.io/">Mustache</a> template to convert an object to text.
 */
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
                return Mustache.compiler().withEscaper(asJMustacheEscaping(escaping)).compile(r);
            } finally {
                r.close();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid template " + templateURL, e);
        }
    }

    private static Mustache.Escaper asJMustacheEscaping(final TextFilter escaping) {
        return new Mustache.Escaper() {
            @Override
            public String escape(String raw) {
                return escaping.filter(raw);
            }
        };
    }
}
