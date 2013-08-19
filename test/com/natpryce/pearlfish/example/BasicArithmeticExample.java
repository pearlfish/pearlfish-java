package com.natpryce.pearlfish.example;


import com.natpryce.pearlfish.Scenario;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.adaptor.junit.Pearlfish;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

import static com.natpryce.pearlfish.Results.results;
import static com.natpryce.pearlfish.Results.scenario;
import static com.natpryce.pearlfish.Results.section;
import static com.natpryce.pearlfish.formats.Formats.MARKDOWN;

@SuppressWarnings("unchecked")
public class BasicArithmeticExample {
    public @Rule ApprovalRule<Object> approvals = Pearlfish.approvalRule("test", MARKDOWN);

    /** This is the code that we are testing
     */
    Calculator calculator = new Calculator();

    @Test
    public void addition() throws IOException {
        approvals.check(results(
                section("basic",
                        addition("simple add", 1, 2),
                        addition("zero left", 0, 2),
                        addition("zero right", 1, 0),
                        addition("zero both", 0, 0)),
                section("negative",
                        addition("negative left", -4, 2),
                        addition("negative right", 5, -4),
                        addition("negative left and zero", -1, 0),
                        addition("zero and negative right", 0, -6),
                        addition("both negative", -4, -9)),
                section("large",
                        addition("large addition", Integer.MAX_VALUE, Integer.MAX_VALUE),
                        addition("large negative numbers", Integer.MIN_VALUE, Integer.MIN_VALUE))));
    }

    @Test
    public void multiplication() throws IOException {
        approvals.check(results(
                section("basic",
                        multiplication("simple multiplication", 2, 5),
                        multiplication("zero left", 0, 2),
                        multiplication("zero right", 1, 0),
                        multiplication("zero both", 0, 0)),
                section("negative",
                        multiplication("negative left", -4, 2),
                        multiplication("negative right", 5, -4),
                        multiplication("negative left and zero", -1, 0),
                        multiplication("zero and negative right", 0, -6),
                        multiplication("both negative", -4, -9)),
                section("large",
                        multiplication("large addition", Integer.MAX_VALUE, 2),
                        multiplication("large negative numbers", Integer.MIN_VALUE, 2))));
    }

    private Scenario<Operands, BigInteger> addition(final String description, int x, int y) {
        calculator.push(x);
        calculator.push(y);
        calculator.add();
        return scenario(description, new Operands(x, y), calculator.pop());
    }

    private Scenario<Operands, BigInteger> multiplication(final String description, int x, int y) {
        calculator.push(x);
        calculator.push(y);
        calculator.mul();
        return scenario(description, new Operands(x, y), calculator.pop());
    }
}
