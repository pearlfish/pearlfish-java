package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.google.common.collect.Sets.newTreeSet;

public class FactBase {
    private List<Facts> allFacts = Lists.newArrayList();

    public Facts newFact(String name, String... types) {
        Facts facts = new Facts(name, types);
        allFacts.add(facts);
        return facts;
    }

    public void writePrologSyntax(PrintWriter writer) throws IOException {
        writeTypePredicates(typePredicates(), writer);
        writeFactPredicates(writer);
    }

    private void writeFactPredicates(PrintWriter writer) throws IOException {
        for (Facts facts : allFacts) {
            writer.println();
            facts.writePrologSyntax(writer);
        }
    }

    private void writeTypePredicates(Multimap<String, String> typeToAtom, PrintWriter writer) {
        for (String typeName : newTreeSet(typeToAtom.keySet())) {
            for (String atom : typeToAtom.get(typeName)) {
                writer.println(typeName + "(" + atom + ").");
            }
            writer.println();
        }
    }

    private Multimap<String, String> typePredicates() {
        Multimap<String,String> typeToAtom = TreeMultimap.create();
        for (Facts facts : allFacts) {
            facts.addTypeInformation(typeToAtom);
        }
        return typeToAtom;
    }

}
