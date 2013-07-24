package com.natpryce.pearlfish.example;


import com.natpryce.pearlfish.junit.Pearlfish;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;
import java.math.BigInteger;

import static com.natpryce.pearlfish.Results.*;
import static com.natpryce.pearlfish.formats.Formats.MARKDOWN;

public class BasicMultiplicationExample {
    public @Rule ApprovalsRule approvals = Pearlfish.approvalRule("test", MARKDOWN);

    @Test
    @SuppressWarnings("unchecked")
    public void multiplication() throws IOException {
        approvals.assertApproved(results(
                section("basic",
                        scenario("simple multiplication", new Operands(2, 5), BigInteger.valueOf(10)),
                        scenario("zero left", new Operands(0, 2), BigInteger.valueOf(0)),
                        scenario("zero right", new Operands(1, 0), BigInteger.valueOf(0)),
                        scenario("zero both", new Operands(0, 0), BigInteger.valueOf(0))),
                section("negative",
                        scenario("negative left", new Operands(-4, 2), BigInteger.valueOf(-8)),
                        scenario("negative right", new Operands(5, -4), BigInteger.valueOf(-20)),
                        scenario("negative left and zero", new Operands(-1, 0), BigInteger.valueOf(0)),
                        scenario("zero and negative right", new Operands(0, -6), BigInteger.valueOf(0)),
                        scenario("both negative", new Operands(-4, -9), BigInteger.valueOf(36))),
                section("large",
                        scenario("large addition", new Operands(Integer.MAX_VALUE, 2), BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(2))),
                        scenario("large negative numbers", new Operands(Integer.MIN_VALUE, 2), BigInteger.valueOf(Integer.MIN_VALUE).multiply(BigInteger.valueOf(2))))));
    }

}
