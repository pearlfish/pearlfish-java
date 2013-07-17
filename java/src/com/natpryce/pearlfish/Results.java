package com.natpryce.pearlfish;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.SortedMap;

import static java.util.Arrays.asList;

public class Results {
    public static <I,O> Scenario<I,O> scenario(String s, I input, O output) {
        return new Scenario<I,O>(s, input, output);
    }

    public static List<Scenario> results(Scenario ... scenarios) {
        return asList(scenarios);
    }

    public static <T> SortedMap<String, ImmutableList<T>> results(Section<T> ... sections) {
        ImmutableSortedMap.Builder<String, ImmutableList<T>> builder = ImmutableSortedMap.naturalOrder();
        for (Section<T> section : sections) {
            builder.put(section.name, section.parts);
        }
        return builder.build();
    }

    public static <T> Section<T> section(String name, T... results) {
        return new Section<T>(name, asList(results));
    }
}
