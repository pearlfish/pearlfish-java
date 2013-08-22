package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.PrintWriter;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.collect.Iterables.transform;
import static com.natpryce.pearlfish.unstable.prolog.PrologEscaping.toAtom;
import static java.util.Arrays.asList;

public class Fact {
    public final boolean isTrue;
    public final ImmutableList<String> paramAtoms;

    public Fact(boolean isTrue, Object... params) {
        this.isTrue = isTrue;
        this.paramAtoms = ImmutableList.copyOf(transform(asList(params), compose(toAtom, toStringFunction())));
    }

    public void writePrologSyntax(PrintWriter writer, String name) throws IOException {
        if (!isTrue) writer.print(":- ");
        writer.print(name);
        writer.print("(");
        Joiner.on(", ").appendTo(writer, paramAtoms);
        writer.println(").");
    }

}
