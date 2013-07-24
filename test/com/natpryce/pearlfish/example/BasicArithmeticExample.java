package com.natpryce.pearlfish.example;


import com.natpryce.pearlfish.formats.Formats;
import com.natpryce.pearlfish.junit.Pearlfish;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;
import java.math.BigInteger;

import static com.natpryce.pearlfish.Results.*;
import static com.natpryce.pearlfish.formats.Formats.MARKDOWN;

public class BasicArithmeticExample {
    public @Rule ApprovalsRule approvals = Pearlfish.pearlfishApprovalsRule("test", MARKDOWN);

    @Test
    @SuppressWarnings("unchecked")
    public void addition() throws IOException {
        approvals.assertApproved(results(
                section("basic",
                        scenario("simple add", new Operands(1, 2), BigInteger.valueOf(3)),
                        scenario("zero left", new Operands(0, 2), BigInteger.valueOf(2)),
                        scenario("zero right", new Operands(1, 0), BigInteger.valueOf(1)),
                        scenario("zero both", new Operands(0, 0), BigInteger.valueOf(0))),
                section("negative",
                        scenario("negative left", new Operands(-4, 2), BigInteger.valueOf(-2)),
                        scenario("negative right", new Operands(5, -4), BigInteger.valueOf(1)),
                        scenario("negative left and zero", new Operands(-1, 0), BigInteger.valueOf(-1)),
                        scenario("zero and negative right", new Operands(0, -6), BigInteger.valueOf(-6)),
                        scenario("both negative", new Operands(-4, -9), BigInteger.valueOf(-13))),
                section("large",
                        scenario("large addition", new Operands(Integer.MAX_VALUE, Integer.MAX_VALUE), BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.valueOf(Integer.MAX_VALUE))),
                        scenario("large negative numbers", new Operands(Integer.MIN_VALUE, Integer.MIN_VALUE), BigInteger.valueOf(Integer.MIN_VALUE).add(BigInteger.valueOf(Integer.MIN_VALUE))))));
    }

    public static class Operands {
        public int x, y;

        public Operands(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
