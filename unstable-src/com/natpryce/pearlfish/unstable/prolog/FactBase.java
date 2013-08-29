package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static com.natpryce.pearlfish.unstable.prolog.PrologFormat.toAtoms;

public class FactBase {
    private List<Relation> allFacts = Lists.newArrayList();
    private Set<String> builtinTypes = newHashSet("number", "nat", "int", "float");

    public Relation newFact(String name, String... types) {
        Relation relation = new Relation(name, types);
        allFacts.add(relation);
        return relation;
    }

    public void writePrologSyntax(PrintWriter writer) throws IOException {
        writeTypePredicates(typePredicates(), writer);
        writeFactPredicates(writer);
    }

    private void writeFactPredicates(PrintWriter writer) throws IOException {
        for (Relation relation : allFacts) {
            writer.println();
            relation.writePrologSyntax(writer);
        }
    }

    private void writeTypePredicates(Multimap<String, Object> typeToValue, PrintWriter writer) {
        for (String typeName : newTreeSet(typeToValue.keySet())) {
            for (String valueAtom : newTreeSet(toAtoms(typeToValue.get(typeName)))) {
                writer.println(typeName + "(" + valueAtom + ").");
            }
            writer.println();
        }
    }

    private Multimap<String, Object> typePredicates() {
        Multimap<String,Object> typeToValue = HashMultimap.create();
        for (Relation relation : allFacts) {
            relation.addTypeInformation(typeToValue);
        }

        typeToValue.keySet().removeAll(builtinTypes);

        return typeToValue;
    }
}
