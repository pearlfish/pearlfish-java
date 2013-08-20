package com.natpryce.pearlfish.adaptor.junit;

import com.natpryce.pearlfish.Approver;
import com.natpryce.pearlfish.FileNamingConvention;
import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.TestSpecific;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;

public class ApprovalRule<T> implements TestRule {
    private final TestSpecific<? extends Format<? super T>> format;
    private final TestSpecific<? extends FileNamingConvention> namingConvention;

    private Approver<T> approver = null;

    public ApprovalRule(TestSpecific<? extends Format<? super T>> format,
                        TestSpecific<? extends FileNamingConvention> namingConvention)
    {
        this.namingConvention = namingConvention;
        this.format = format;
    }

    public void check(T receivedContents) throws IOException {
        approver().check(receivedContents);
    }

    public void recordAsApproved(T receivedContents) throws IOException {
        approver().recordAsApproved(receivedContents);
    }

    private Approver<T> approver() {
        if (approver == null) {
            throw new IllegalStateException("cannot perform approval before discovering the name of the test (did you forget to annotate the approver with @Rule?)");
        }

        return approver;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        Class<?> testClass = description.getTestClass();
        String testName = description.getMethodName();

        approver = new Approver<T>(
                namingConvention.forTest(testClass, testName),
                format.forTest(testClass, testName),
                new JUnitDifferenceReporter());

        return base;
    }
}
