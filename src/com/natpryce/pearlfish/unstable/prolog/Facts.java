package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.PrintWriter;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Iterables.transform;
import static java.util.Arrays.asList;

public class Facts {
    public final String name;
    public final ImmutableList<ImmutableList<String>> modes;
    public final ImmutableList<Fact> facts;

    public Facts(String name, Iterable<ImmutableList<String>> modes, Iterable<Fact> facts) {
        this.name = name;
        this.modes = ImmutableList.copyOf(modes);
        this.facts = ImmutableList.copyOf(facts);
    }

    public int arity() {
        return modes.get(0).size();
    }

    public void writePrologSyntax(PrintWriter writer) throws IOException {
        PrologEscaping escaping = new PrologEscaping();

        for (ImmutableList<String> mode : modes) {
            writer.println(modeToPrologSyntax(mode, escaping));
        }

        for (Fact fact : facts) {
            writer.println(factToPrologSyntax(fact, escaping));
        }
    }

    private String modeToPrologSyntax(ImmutableList<String> mode, PrologEscaping escaping) {
        return "modeh(1," + escaping.apply(name) + "(" + on(", ").join(mode) + "))?";
    }

    private String factToPrologSyntax(Fact fact, PrologEscaping escaping) {
        Iterable<String> paramAtoms = transform(asList(fact.params), compose(escaping, toStringFunction()));

        return (fact.isTrue ? "" : ":- ") + escaping.apply(name) + "(" + on(", ").join(paramAtoms) + ").";
    }
}
