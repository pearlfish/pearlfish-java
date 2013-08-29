package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.base.Function;
import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import static com.google.common.collect.Iterables.transform;

public class PrologFormat implements Format<FactBase> {
    private static final Pattern plainAtomPattern = Pattern.compile("[a-z][a-zA-Z0-9_]*");

    public static final TestSpecific<PrologFormat> PROLOG = new TestSpecific<PrologFormat>() {
        @Override
        public PrologFormat forTest(Class<?> testClass, String testName) {
            return new PrologFormat();
        }
    };

    public static final Function<Object,String> toAtom = new Function<Object, String>() {
        @Override
        public String apply(Object raw) {
            return toAtom(raw);
        }
    };

    public static String toAtom(Object raw) {
        if (raw.equals(Boolean.TRUE)) {
            return "yes";
        }
        else if (raw.equals(Boolean.FALSE)) {
            return "no";
        }
        else if (raw instanceof Number) {
            return raw.toString();
        }
        else if (raw == Fact.ANYTHING) {
            return "_";
        }
        else {
            String stringForm = raw.toString();
            if (isPlainAtom(stringForm)) {
                return stringForm;
            }
            else {
                return quotedAtom(stringForm);
            }
        }
    }

    private static String quotedAtom(String raw) {
        return "'" + raw.replace("'", "\'") + "'";
    }

    private static boolean isPlainAtom(String raw) {
        return plainAtomPattern.matcher(raw).matches();
    }

    public static Iterable<String> toAtoms(Iterable<Object> values) {
        return transform(values, toAtom);
    }

    @Override
    public void write(FactBase facts, OutputStream output) throws IOException {
        PrintWriter writer = new PrintWriter(output);
        facts.writePrologSyntax(writer);
        writer.flush();
    }

    @Override
    public String fileExtension() {
        return ".pl";
    }

    @Override
    public FormatType fileType() {
        return FormatType.of("text", "prolog");
    }
}
