package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.Results;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.natpryce.pearlfish.Results.results;
import static com.natpryce.pearlfish.Results.section;
import static java.util.Arrays.asList;

public class YamlFormatterTest {
    public
    @Rule
    ApprovalsRule approvals = ApprovalsRule.fileSystemRule("test");

    YamlFormatter formatter = new YamlFormatter(Charset.defaultCharset());

    @Test
    public void yamlFormattingOfPearlfishDataStructures() throws IOException {
        approvals.assertApproved(
                formatter.formatted(results(
                        section("multiplying",
                                Results.scenario("double", asList(1, 2, 4), asList(2, 4, 8)),
                                Results.scenario("triple", asList(1, 2, 4), asList(3, 6, 12))),
                        section("adding",
                                Results.scenario("+1", asList(1, 2, 4), asList(2, 3, 5)),
                                Results.scenario("+2", asList(1, 2, 4), asList(3, 4, 6))))));
    }
}
