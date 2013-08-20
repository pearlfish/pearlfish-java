package com.natpryce.pearlfish.adaptor.junit;

import org.junit.ComparisonFailure;

import java.io.File;

public class ApprovalFailure extends ComparisonFailure {
    public final File approvedFile;
    public final String approvedContents; // empty string if approvedFile does not exist.
    public final File receivedFile;
    public final String receivedContents;

    public ApprovalFailure(String message, File approvedFile, String approvedContents, File receivedFile, String receivedContents) {
        super(message, approvedContents, receivedContents);

        this.approvedFile = approvedFile;
        this.approvedContents = approvedContents;
        this.receivedFile = receivedFile;
        this.receivedContents = receivedContents;
    }
}
