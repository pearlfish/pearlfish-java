package info.pearlfish.internal;

import info.pearlfish.formats.TextFilter;

import java.util.Arrays;

public class PrefixedEscaping implements TextFilter {
    private char escapeChar;
    private final char[] charsToEscape;

    public PrefixedEscaping(char... charsToEscape) {
        this.charsToEscape = charsToEscape;
        this.escapeChar = charsToEscape[0];

        Arrays.sort(charsToEscape);
    }

    @Override
    public String filter(String raw) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (mustEscape(c)) {
                sb.append(escapeChar);
            }
            sb.append(c);
        }

        return sb.toString();
    }

    private boolean mustEscape(char c) {
        return Arrays.binarySearch(charsToEscape, c) >= 0;
    }
}
