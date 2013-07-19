package com.natpryce.pearlfish.example;


import com.natpryce.pearlfish.Scenario;
import com.natpryce.pearlfish.junit.Pearlfish;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;
import java.util.List;

import static com.natpryce.pearlfish.Results.results;
import static com.natpryce.pearlfish.Results.scenario;
import static java.util.Arrays.asList;

public class BasicArithmeticExample {
    public @Rule ApprovalsRule approvals = Pearlfish.pearlfishApprovalsRule("test");

    @Test
    public void addition() throws IOException {
        approvals.assertApproved(results(
                addition("simple add", 1, 2),
                addition("zero left", 0, 2),
                addition("zero right", 1, 0),
                addition("zero both", 0, 0)));
    }

    private Scenario<List<Integer>, Integer> addition(String what, int x, int y) {
        return scenario(what, asList(x, y), x + y);
    }
}
