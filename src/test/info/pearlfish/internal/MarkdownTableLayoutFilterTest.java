package info.pearlfish.internal;

import com.google.common.base.Joiner;
import info.pearlfish.adaptor.junit.ApprovalRule;
import info.pearlfish.formats.Formats;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class MarkdownTableLayoutFilterTest {
    @Rule public ApprovalRule<String> approvals = InternalApprovals.selfTestApprover(Formats.STRING);

    MarkdownTableLayoutFilter filter = new MarkdownTableLayoutFilter();

    @Test
    public void reformatsTablesInMarkdownPipeTableFormat() throws IOException {
        String markdown = md(
                "A Header",
                "========",
                "",
                "The column dividers in the table below",
                "should be lined up:",
                "",
                "| Column 1 | Column 2 | Column 3 |",
                "| cell A1 | cell A2 | cell A3 |",
                "| long cell B1 | B2 | B3 |",
                "",
                "and some more text...");

        approvals.check(filter.filter(markdown));
    }

    @Test
    public void reformatsTablesWithHeaderDividerRows() throws IOException {
        String markdown = md(
                "| Header 1 | Header 2 | Header 3 |",
                "|----------|---------:|:---------|",
                "| cell A1 | long cell A2 | cell A3 |",
                "| long cell B1 | B2 | long cell B3 |");

        approvals.check(filter.filter(markdown));
    }

    @Test
    public void reformatsTablesWithEscapedPipes() throws IOException {
        String markdown = md(
                "| Column 1 | Column 2 | Column 3 |",
                "| cell\\|A1\\| | cell\\|A2\\| | cell\\|A3\\| |");

        approvals.check(filter.filter(markdown));
    }

    private String md(String... lines) {
        return Joiner.on("\n").join(lines);
    }
}
