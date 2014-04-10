package info.pearlfish.internal;

import info.pearlfish.formats.TextFilter;

public class ReplaceEscaping implements TextFilter {
    private final String[][] replacements;

    public ReplaceEscaping(String[][] replacements) {
        this.replacements = replacements;
    }


    @Override
    public String filter(String input) {
        String result = input;
        for (String[] replacement : replacements) {
            result=result.replace(replacement[0], replacement[1]);
        }
        return result;
    }
}
