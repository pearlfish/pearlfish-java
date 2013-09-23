package com.natpryce.pearlfish.adaptor.junit;

import com.natpryce.pearlfish.DifferenceReporter;
import com.natpryce.pearlfish.FormatType;

import java.io.File;
import java.io.IOException;

public abstract class JUnitDifferenceReporter implements DifferenceReporter {
    @Override
    public void reportDifference(FormatType formatType, File approvedFile, File receivedFile)
            throws IOException
    {
        String receivedContents = loadContents(receivedFile);
        String approvedContents;
        String message;

        if (approvedFile.exists()) {
            message = "approval required";
            approvedContents = loadContents(approvedFile);
        } else {
            message = "no approved results found";
            approvedContents = "";
        }

        String fullMessage = message +
                "\n  approved file: " + approvedFile +
                "\n  received file: " + receivedFile;

        throw new ApprovalFailure(fullMessage, approvedFile, approvedContents, receivedFile, receivedContents);
    }

    protected abstract String loadContents(File file) throws IOException;
}
