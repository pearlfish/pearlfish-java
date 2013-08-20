package com.natpryce.pearlfish.adaptor.junit;

import com.google.common.io.Files;
import com.natpryce.pearlfish.DifferenceReporter;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.internal.IO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class JUnitDifferenceReporter implements DifferenceReporter {
    @Override
    public void reportDifference(FormatType formatType, File approvedFile, File receivedFile)
            throws IOException {
        String receivedContents = loadContents(formatType, receivedFile);
        String approvedContents;
        String message;

        if (approvedFile.exists()) {
            message = "approval required";
            approvedContents = loadContents(formatType, approvedFile);
        } else {
            message = "no approved results found";
            approvedContents = "";
        }

        String fullMessage = message +
                "\n  approved file: " + approvedFile +
                "\n  received file: " + receivedFile;

        throw new ApprovalFailure(fullMessage, approvedFile, approvedContents, receivedFile, receivedContents);
    }

    private String loadContents(FormatType formatType, File file) throws IOException {
        if (formatType.mostGeneralType().equals(FormatType.TEXT_TYPE)) {
            return Files.toString(file, Charset.defaultCharset());
        } else {
            return IO.toHexDump(file);
        }
    }
}
