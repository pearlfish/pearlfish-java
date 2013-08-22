package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static com.natpryce.pearlfish.unstable.prolog.PrologEscaping.toAtom;
import static java.util.Arrays.asList;

public class Facts implements Iterable<Fact> {
    public final String name;
    public final ImmutableList<String> types;

    private final List<Fact> facts = Lists.newArrayList();

    public Facts(String name, String... types) {
        this.name = toAtom(name);
        this.types = ImmutableList.copyOf(transform(asList(types), toAtom));
    }

    public void declare(boolean isTrue, Object... params) {
        if (params.length != arity()) {
            throw new IllegalArgumentException("wrong arity: " + params.length + " params added to " + name + "/" + arity());
        }

        facts.add(new Fact(isTrue, params));
    }

    private int arity() {
        return types.size();
    }

    @Override
    public Iterator<Fact> iterator() {
        return facts.iterator();
    }

    public void addTypeInformation(Multimap<String,String> typeToAtom) {
        for (Fact fact : facts) {
            for (int i = 0; i < fact.paramAtoms.size(); i++) {
                String type = types.get(i);
                String param = fact.paramAtoms.get(i);

                typeToAtom.put(type, param);
            }
        }
    }

    public void writePrologSyntax(PrintWriter writer) throws IOException {
        for (Fact fact : facts) {
            fact.writePrologSyntax(writer, name);
        }
    }
}
