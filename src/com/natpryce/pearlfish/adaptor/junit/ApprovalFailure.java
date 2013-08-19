package com.natpryce.pearlfish.adaptor.junit;

import com.natpryce.pearlfish.ApprovalError;
import org.junit.ComparisonFailure;

import java.io.File;

public class ApprovalFailure extends ComparisonFailure {
    public final File approvedFile;
    public final String approvedContents; // Can be null, should split into two exception types
    public final File receivedFile;
    public final String receivedContents;

    public ApprovalFailure(String message, File approvedFile, String approvedContents, File receivedFile, String receivedContents) {
        super(message, approvedContents, receivedContents);

        this.approvedFile = approvedFile;
        this.approvedContents = approvedContents;
        this.receivedFile = receivedFile;
        this.receivedContents = receivedContents;
    }

    public ApprovalFailure(ApprovalError original) {
        this(original.getMessage(),
             original.approvedFile, original.approvedContents,
             original.receivedFile, original.receivedContents);
    }
}
