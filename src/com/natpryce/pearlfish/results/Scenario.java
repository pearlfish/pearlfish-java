package com.natpryce.pearlfish.results;

public class Scenario<I,O> {
    public final String name;
    public final I input;
    public final O output;

    public Scenario(String name, I input, O output) {
        this.name = name;
        this.input = input;
        this.output = output;
    }
}
