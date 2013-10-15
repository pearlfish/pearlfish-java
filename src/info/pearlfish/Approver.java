package info.pearlfish;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.google.common.io.Files.newInputStreamSupplier;

/**
 * The core Pearlfish functionality that actually performs the approval testing.
 *
 * In practice you will probably not use this class directly but use an adaptor that adapts it to a test framework,
 * such as the {@link info.pearlfish.adaptor.junit.ApprovalRule} for JUnit 4.
 *
 * @param <T> the type of value to be approved
 */
public class Approver<T> {
    private final Format<? super T> format;
    private final FileNamingConvention namingConvention;
    private final DifferenceReporter differenceReporter;

    /**
     * Constructs a new Approver.
     *
     * @param namingConvention defines where received and approved files are located and how they are named
     * @param format writes values of type <var>T</var> to binary streams
     * @param differenceReporter reports the difference between approved and received files.
     */
    public Approver(FileNamingConvention namingConvention, Format<? super T> format, DifferenceReporter differenceReporter) {
        this.namingConvention = namingConvention;
        this.format = format;
        this.differenceReporter = differenceReporter;
    }

    /**
     * Check that <var>receivedContents</var> is approved.
     *
     * If the value is not as approved, the <var>differenceReporter</var> is notified and the received file
     * is left on disk.
     *
     * If the value is approved, the received file is deleted.
     *
     * @param receivedContents the value to check
     * @throws IOException the value cannot be written to the file system or the approved version cannot
     *                     be read from the file system.
     */
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

    /**
     * Record the <var>receivedContents</var> as the approved version.
     *
     * @param receivedContents the value to record
     * @throws IOException the value cannot be written to the file system
     */
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
