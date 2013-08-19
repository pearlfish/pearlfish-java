package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class YamlFormat implements Format<Object> {
    private final DumperOptions options = new DumperOptions();
    private final Representer representer = new Representer();
    private final Charset charset;

    public YamlFormat(Charset charset) {
        this.charset = charset;
        this.options.setAllowReadOnlyProperties(true);
    }

    @Override
    public void write(Object value, OutputStream output) throws IOException {
        Yaml yaml = new Yaml(representer, options);
        OutputStreamWriter writer = new OutputStreamWriter(output, charset);
        // Note - dumping to writer does not seem to work!
        writer.write(yaml.dump(value));
        writer.flush();
    }

    @Override
    public String fileExtension() {
        return ".yaml";
    }

    @Override
    public FormatType fileType() {
        return FormatType.of("text", "yaml");
    }
}
