package info.pearlfish.internal;

import com.google.common.base.Strings;
import info.pearlfish.formats.TextFilter;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;

public class MarkdownTableLayoutFilter implements TextFilter {
    private static final Pattern tableRowPattern = Pattern.compile("^(?<!\\\\)\\|.*(?<!\\\\)\\|\\s*$");
    private static final Pattern tableDividerPattern = Pattern.compile(":-+|-+|-+:");
    private static final Pattern columnDividerPattern = Pattern.compile("(?<!\\\\)\\|");

    @Override
    public String filter(String input) {
        try {
            LineReader lines = new LineReader(new StringReader(input));

            StringBuilder filtered = new StringBuilder();
            formatTablesInMarkdownText(lines, filtered);

            return filtered.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("malformed input data", e);
        }
    }

    private void formatTablesInMarkdownText(Lines lines, StringBuilder filtered) throws IOException {
        String line;
        while ((line = lines.readLine()) != null) {
            if (isTableRowRow(line)) {
                lines.pushBack(line);
                formatTable(parseTableRows(lines), filtered);
            }
            else {
                filtered.append(line);
                filtered.append("\n");
            }
        }
    }

    private boolean isTableRowRow(String line) {
        return tableRowPattern.matcher(line).matches();
    }

    private boolean isTableDividerRow(String[] row) {
        for (String cell : row) {
            if (!isTableDividerCell(cell)) return false;
        }
        return true;
    }

    private boolean isTableDividerCell(String cell) {
        return tableDividerPattern.matcher(cell).matches();
    }

    private List<String[]> parseTableRows(Lines lines) throws IOException {
        List<String[]> rows = newArrayList();
        String line;

        while ((line = lines.readLine()) != null) {
            if (isTableRowRow(line)) {
                String[] parts = columnDividerPattern.split(line);
                rows.add(Arrays.asList(parts).subList(1, parts.length).toArray(new String[parts.length-1]));
            }
            else {
                lines.pushBack(line);
                break;
            }
        }
        return rows;
    }

    private void formatTable(List<String[]> rows, StringBuilder filtered) {
        int[] columnWidths = columnWidths(rows);

        for (String[] row : rows) {
            filtered.append("|");

            if (isTableDividerRow(row)) {
                for (int i = 0; i < row.length; i++) {
                    filtered.append(padDivider(row[i], columnWidths[i]));
                    filtered.append("|");
                }
            }
            else {
                for (int i = 0; i < row.length; i++) {
                    filtered.append(padCell(row[i], columnWidths[i]));
                    filtered.append("|");
                }
            }
            filtered.append("\n");
        }
    }

    private String padDivider(String divider, int columnWidth) {
        if (divider.endsWith(":")) {
            return Strings.padStart(divider, columnWidth, '-');
        }
        else {
            return Strings.padEnd(divider, columnWidth, '-');
        }
    }

    private String padCell(String cell, int colWidth) {
        return Strings.padEnd(cell, colWidth, ' ');
    }

    private int[] columnWidths(List<String[]> rows) {
        int columnWidths[] = new int[maxLength(rows)];

        for (String[] row : rows) {
            for (int i = 0, rowLength = row.length; i < rowLength; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        return columnWidths;
    }

    private int maxLength(List<String[]> rows) {
        int maxLength = 0;
        for (String[] row : rows) {
            maxLength += Math.max(row.length, maxLength);
        }
        return maxLength;
    }
}
