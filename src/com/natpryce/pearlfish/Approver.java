package com.natpryce.pearlfish;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.google.common.io.Files.newInputStreamSupplier;

public class Approver<T> {
    private final Format<? super T> format;
    private final FileNamingConvention namingConvention;
    private final DifferenceReporter differenceReporter;

    public Approver(FileNamingConvention namingConvention, Format<? super T> format, DifferenceReporter differenceReporter) {
        this.namingConvention = namingConvention;
        this.format = format;
        this.differenceReporter = differenceReporter;
    }

    public void check(T receivedContents) throws IOException {
        File receivedFile = receivedFile();
        File approvedFile = approvedFile();

        writeContents(receivedFile, receivedContents);

        if (!approvedFile.exists() || !haveTheSameContents(receivedFile, approvedFile)) {
            differenceReporter.reportDifference(format.fileType(), approvedFile, receivedFile);
        }

        //noinspection ResultOfMethodCallIgnored
        receivedFile.delete();
    }

    public void recordAsApproved(T receivedContents) throws IOException {
        writeContents(approvedFile(), receivedContents);

        //noinspection ResultOfMethodCallIgnored
        receivedFile().delete();
    }

    private File approvedFile() {
        return namingConvention.approvedFile(format.fileExtension());
    }

    private File receivedFile() {
        return namingConvention.receivedFile(format.fileExtension());
    }

    private boolean haveTheSameContents(File receivedFile, File approvedFile) throws IOException {
        return ByteStreams.equal(newInputStreamSupplier(receivedFile),
                newInputStreamSupplier(approvedFile));
    }

    private void writeContents(File file, T value) throws IOException {
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();

        OutputStream out = new FileOutputStream(file);
        try {
            format.write(value, out);
        }
        finally {
            out.close();
        }
    }
}
