package com.natpryce.pearlfish.internal;

import com.samskivert.mustache.Escaping;

import java.util.regex.Pattern;

public class PrologEscaping implements Escaping {
    private final Pattern numberPattern = Pattern.compile("[0-9]+(\\.[0-9]+)?");
    private final Pattern plainAtomPattern = Pattern.compile("[a-z][a-z_]*");

    @Override
    public String escape(String raw) {
        if (isNumber(raw) || isPlainAtom(raw)) {
            return raw;
        } else {
            return quotedAtom(raw);
        }
    }

    private String quotedAtom(String raw) {
        return "'" + raw.replace("'", "\'") + "'";
    }

    private boolean isNumber(String raw) {
        return numberPattern.matcher(raw).matches();
    }

    private boolean isPlainAtom(String raw) {
        return plainAtomPattern.matcher(raw).matches();
    }
}
