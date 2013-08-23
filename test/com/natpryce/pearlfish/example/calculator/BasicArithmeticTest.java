package com.natpryce.pearlfish.example.calculator;


import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.results.Scenario;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

import static com.natpryce.pearlfish.formats.Formats.MARKDOWN;
import static com.natpryce.pearlfish.results.Results.*;

@SuppressWarnings("unchecked")
public class BasicArithmeticTest {
    @Rule
    public ApprovalRule<Object> approval = new ApprovalRule<Object>("test", MARKDOWN);

    /**
     * This is the code that we are testing
     */
    Calculator calculator = new Calculator();

    @Test
    public void additionFirstTest() throws IOException {
        approval.check(results(
                addition("simple add", 1, 2),
                addition("zero left", 0, 2),
                addition("zero right", 1, 0),
                addition("zero both", 0, 0)));
    }

    @Test
    public void addition() throws IOException {
        approval.check(results(
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
                        addition("large addition", Long.MAX_VALUE, Long.MAX_VALUE),
                        addition("large negative numbers", Long.MIN_VALUE, Long.MIN_VALUE))));
    }

    @Test
    public void multiplication() throws IOException {
        approval.check(results(
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
                        multiplication("large addition", Long.MAX_VALUE, 2),
                        multiplication("large negative numbers", Long.MIN_VALUE, 2))));
    }

    private Scenario<Operands, BigInteger> addition(final String description, long x, long y) {
        calculator.push(x);
        calculator.push(y);
        calculator.add();
        return scenario(description, new Operands(x, y), calculator.pop());
    }

    private Scenario<Operands, BigInteger> multiplication(final String description, long x, long y) {
        calculator.push(x);
        calculator.push(y);
        calculator.mul();
        return scenario(description, new Operands(x, y), calculator.pop());
    }
}
