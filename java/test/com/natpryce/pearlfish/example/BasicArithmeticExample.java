package com.natpryce.pearlfish.example;


import com.natpryce.pearlfish.junit.Pearlfish;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;

import static com.natpryce.pearlfish.Results.results;
import static com.natpryce.pearlfish.Results.scenario;
import static java.util.Arrays.asList;

public class BasicArithmeticExample {
    public @Rule ApprovalsRule approvals = Pearlfish.pearlfishApprovalsRule("test");

    @Test
    public void addition() throws IOException {
        approvals.assertApproved(results(
                scenario("simple add", asList(1, 2), 3),
                scenario("zero left",  asList(0, 2), 2),
                scenario("zero right", asList(1, 0), 1),
                scenario("zero both",  asList(0, 0), 0)));
    }
}
