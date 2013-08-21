package com.natpryce.pearlfish.adaptor.junit;

import com.google.common.io.Files;
import com.natpryce.pearlfish.Approver;
import com.natpryce.pearlfish.DifferenceReporter;
import com.natpryce.pearlfish.FileNamingConvention;
import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.internal.IO;
import com.natpryce.pearlfish.internal.TypeDirectedDifferenceReporter;
import com.natpryce.pearlfish.naming.NextToSourceNamingConvention;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class ApprovalRule<T> implements TestRule {
    private final TestSpecific<? extends Format<? super T>> format;
    private final TestSpecific<? extends FileNamingConvention> namingConvention;

    private final TypeDirectedDifferenceReporter reporter;

    private Approver<T> approver = null;

    public ApprovalRule(String sourceDir, TestSpecific<? extends Format<? super T>> format) {
        this(format, NextToSourceNamingConvention.forSourceDirectory(sourceDir));
    }

    public ApprovalRule(TestSpecific<? extends Format<? super T>> format,
                        TestSpecific<? extends FileNamingConvention> namingConvention)
    {
        this.namingConvention = namingConvention;
        this.format = format;

        DifferenceReporter binaryReporter = new JUnitDifferenceReporter() {
            @Override
            protected String loadContents(File file) throws IOException {
                return IO.toHexDump(file);
            }
        };
        DifferenceReporter textReporter = new JUnitDifferenceReporter() {
            @Override
            protected String loadContents(File file) throws IOException {
                return Files.toString(file, Charset.defaultCharset());
            }
        };

        reporter = new TypeDirectedDifferenceReporter(binaryReporter);
        reporter.register(FormatType.TEXT_TYPE, textReporter);
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
                reporter);

        return base;
    }
}
