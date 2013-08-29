package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.PrintWriter;

import static com.natpryce.pearlfish.unstable.prolog.PrologFormat.toAtoms;

public class Fact {
    public static final Object ANYTHING = new Object();

    public final boolean isTrue;
    public final ImmutableList<Object> params;

    public Fact(boolean isTrue, Object... params) {
        this.isTrue = isTrue;
        this.params = ImmutableList.copyOf(params);
    }

    public void writePrologSyntax(PrintWriter writer, String name) throws IOException {
        if (!isTrue) writer.print(":- ");
        writer.print(name);
        writer.print("(");
        Joiner.on(", ").appendTo(writer, toAtoms(params));
        writer.println(").");
    }

}
