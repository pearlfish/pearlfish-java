package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Set;

import static com.natpryce.pearlfish.FormatType.TEXT;

public class YamlFormat implements Format<Object> {
    private final DumperOptions options = new DumperOptions();
    private final Charset charset;
    private final Representer representer = new Representer() {
        @Override
        protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
            final MappingNode node = super.representJavaBean(properties, javaBean);
            node.setTag(Tag.MAP);
            return node;
        }
    };

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
        return TEXT.specialised("yaml");
    }
}
