package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.base.Function;
import com.samskivert.mustache.Escaping;

import java.util.regex.Pattern;

public class PrologEscaping implements Function<String, String>, Escaping {
    private static final Pattern numberPattern = Pattern.compile("[0-9]+(\\.[0-9]+)?");
    private static final Pattern plainAtomPattern = Pattern.compile("[a-z][a-z_]*");

    public static String toAtom(String raw) {
        if (isNumber(raw) || isPlainAtom(raw)) {
            return raw;
        } else {
            return quotedAtom(raw);
        }
    }

    private static String quotedAtom(String raw) {
        return "'" + raw.replace("'", "\'") + "'";
    }

    private static boolean isNumber(String raw) {
        return numberPattern.matcher(raw).matches();
    }

    private static boolean isPlainAtom(String raw) {
        return plainAtomPattern.matcher(raw).matches();
    }

    @Override
    public String escape(String raw) {
        return toAtom(raw);
    }

    @Override
    public String apply(String raw) {
        return toAtom(raw);
    }
}
