package info.pearlfish.internal;

import info.pearlfish.adaptor.junit.ApprovalRule;
import info.pearlfish.formats.Formats;
import info.pearlfish.results.Results;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static info.pearlfish.results.Results.results;
import static info.pearlfish.results.Results.section;
import static java.util.Arrays.asList;

public class YamlFormatTest {
    public
    @Rule
    ApprovalRule<String> approvals = InternalApprovals.selfTestApprover(Formats.STRING);

    Charset charset = Charset.defaultCharset();
    YamlFormat formatter = new YamlFormat(charset);

    @Test
    @SuppressWarnings("unchecked")
    public void yamlFormattingOfPearlfishDataStructures() throws IOException {
        approvals.check(
                format(results(
                        section("multiplying",
                                Results.scenario("double", asList(1, 2, 4), asList(2, 4, 8)),
                                Results.scenario("triple", asList(1, 2, 4), asList(3, 6, 12))),
                        section("adding",
                                Results.scenario("+1", asList(1, 2, 4), asList(2, 3, 5)),
                                Results.scenario("+2", asList(1, 2, 4), asList(3, 4, 6))))));
    }

    private String format(Object o) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        formatter.write(o, bytes);
        return bytes.toString(charset.name());
    }
}
