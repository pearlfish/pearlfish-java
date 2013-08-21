package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.DifferenceReporter;
import com.natpryce.pearlfish.FormatType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TypeDirectedDifferenceReporterTest {
    File receivedFile = new File("received");
    File approvedFile = new File("approved");

    FakeDifferenceReporter lastDitchReporter = new FakeDifferenceReporter();

    TypeDirectedDifferenceReporter reporter = new TypeDirectedDifferenceReporter(lastDitchReporter);

    FakeDifferenceReporter a_b_reporter = new FakeDifferenceReporter();
    FakeDifferenceReporter a_reporter = new FakeDifferenceReporter();
    FakeDifferenceReporter x_reporter = new FakeDifferenceReporter();

    {
        reporter.register(FormatType.of("a", "b"), a_b_reporter);
        reporter.register(FormatType.of("a"), a_reporter);
        reporter.register(FormatType.of("x"), x_reporter);
    }

    @Test
    public void delegatesToReporterRegisteredForTheFormatType() throws IOException {
        reporter.reportDifference(FormatType.of("a", "b"), approvedFile, receivedFile);

        assertTrue(a_b_reporter.wasCalled);
    }

    @Test
    public void generalisesTheFormatTypeUntilItFindsAReporter() throws IOException {
        reporter.reportDifference(FormatType.of("a", "s", "t", "u"), approvedFile, receivedFile);

        assertTrue(a_reporter.wasCalled);
    }

    @Test
    public void usesALastDitchReporterIfNoReporterFoundForFormatType() throws IOException {
        reporter.reportDifference(FormatType.of("w", "t", "f"), approvedFile, receivedFile);

        assertTrue(lastDitchReporter.wasCalled);
    }

    @Test
    public void passesFormatTypeAndFilesToSelectedReporter() throws IOException {
        FormatType formatType = FormatType.of("a");

        reporter.reportDifference(formatType, approvedFile, receivedFile);

        assertEquals("format", formatType, a_reporter.formatType);
        assertEquals("received file", receivedFile, a_reporter.receivedFile);
        assertEquals("approved file", approvedFile, a_reporter.approvedFile);
    }

    public static class FakeDifferenceReporter implements DifferenceReporter {
        boolean wasCalled = false;
        FormatType formatType;
        File approvedFile;
        File receivedFile;

        @Override
        public void reportDifference(FormatType formatType, File approvedFile, File receivedFile) throws IOException {
            this.formatType = formatType;
            this.approvedFile = approvedFile;
            this.receivedFile = receivedFile;
            this.wasCalled = true;
        }
    }
}
