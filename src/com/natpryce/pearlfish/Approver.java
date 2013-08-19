package com.natpryce.pearlfish;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static com.google.common.io.Files.newInputStreamSupplier;

public class Approver<T> {
    private final Format<? super T> format;
    private final FileNamingConvention namingConvention;

    public Approver(FileNamingConvention namingConvention, Format<? super T> format) {
        this.namingConvention = namingConvention;
        this.format = format;
    }
    
    public void check(T receivedContents) throws IOException {
        File receivedFile = receivedFile();
        File approvedFile = approvedFile();

        writeContents(receivedFile, receivedContents);

        //TODO - handle binary formats with file types and diff reporters

        if (!approvedFile.exists()) {
            throw new ApprovalError(approvedFile, receivedFile, Files.toString(receivedFile, Charset.defaultCharset()));
        } else if (!haveTheSameContents(receivedFile, approvedFile)) {
            throw new ApprovalError(approvedFile, Files.toString(approvedFile, Charset.defaultCharset()), receivedFile, Files.toString(receivedFile, Charset.defaultCharset()));
        }

        //noinspection ResultOfMethodCallIgnored
        receivedFile.delete();
    }

    public void recordAsApproved(T receivedContents) throws IOException {
        writeContents(approvedFile(), receivedContents);
    }

    private File approvedFile() {
        return namingConvention.approvedFileName(format.extension());
    }

    private File receivedFile() {
        return namingConvention.receivedFileName(format.extension());
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
