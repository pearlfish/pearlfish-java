package com.natpryce.pearlfish.example;


import com.natpryce.pearlfish.junit.Pearlfish;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;

import static com.natpryce.pearlfish.Results.results;
import static com.natpryce.pearlfish.Results.scenario;

public class BasicArithmeticExample {
    public @Rule ApprovalsRule approvals = Pearlfish.pearlfishApprovalsRule("test");

    @Test
    @SuppressWarnings("unchecked")
    public void addition() throws IOException {
        approvals.assertApproved(results(
                scenario("simple add", new Operands(1, 2), 3),
                scenario("zero left", new Operands(0, 2), 2),
                scenario("zero right", new Operands(1, 0), 1),
                scenario("zero both", new Operands(0, 0), 0)));
    }

    public static class Operands {
        public int x, y;

        public Operands(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
