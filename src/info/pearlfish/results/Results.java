package info.pearlfish.results;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Convenience functions for identifying and grouping results.
 */
public class Results {
    public static <T> Section<T> section(String name, T... results) {
        return new Section<T>(name, asList(results));
    }

    public static <I,O> Scenario<I,O> scenario(String named, I input, O output) {
        return new Scenario<I,O>(named, input, output);
    }

    @SuppressWarnings("unchecked")
    public static <I,O> Map<String,List<Scenario<I,O>>> results(Scenario<I,O> ... results) {
        return results(section("results", results));
    }

    public static <T> Map<String, List<T>> results(Section<T> ... sections) {
        LinkedHashMap<String, List<T>> map = new LinkedHashMap<String, List<T>>();
        for (Section<T> section : sections) {
            map.put(section.name, section.parts);
        }
        return map;
    }
}
