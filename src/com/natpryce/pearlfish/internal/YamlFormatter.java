package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.Scenario;
import com.natpryce.pearlfish.Section;
import org.rococoa.okeydoke.formatters.StringFormatter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.nio.charset.Charset;

public class YamlFormatter extends StringFormatter {
    private final DumperOptions options = new DumperOptions();
    private final Representer representer = new Representer();

    public YamlFormatter(Charset charset) {
        super(charset);
        options.setAllowReadOnlyProperties(true);

        addClassTag(Scenario.class, "scenario");
        addClassTag(Section.class, "section");
    }

    public Tag addClassTag(Class<?> type, String tag) {
        return representer.addClassTag(type, new Tag("!" + tag));
    }

    @Override
    public String formatted(Object actual) {
        Yaml yaml = new Yaml(representer, options);
        return yaml.dumpAs(actual, Tag.MAP, DumperOptions.FlowStyle.AUTO);
    }
}
