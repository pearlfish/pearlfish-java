package com.natpryce.pearlfish.example;

import java.math.BigInteger;
import java.util.Stack;

public class Calculator {
    private final Stack<BigInteger> operands = new Stack<BigInteger>();

    public void push(BigInteger operand) {
        operands.push(operand);
    }

    public void push(long operand) {
        push(BigInteger.valueOf(operand));
    }

    public BigInteger pop() {
        return operands.pop();
    }

    public void add() {
        push(pop().add(pop()));
    }

    public void mul() {
        push(pop().multiply(pop()));
    }
}
