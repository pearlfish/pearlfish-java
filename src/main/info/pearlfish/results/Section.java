package info.pearlfish.results;

import com.google.common.collect.ImmutableList;

public class Section<T> {
    public String name;
    public ImmutableList<T> parts;

    public Section(String name, ImmutableList<T> parts) {
        this.name = name;
        this.parts = parts;
    }

    public Section(String name, Iterable<T> parts) {
        this(name, ImmutableList.copyOf(parts));
    }
}
