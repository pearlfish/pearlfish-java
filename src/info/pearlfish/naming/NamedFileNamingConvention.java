package info.pearlfish.naming;

import com.google.common.io.Files;
import info.pearlfish.FileNamingConvention;
import info.pearlfish.TestSpecific;

import java.io.File;

/**
 * Writes a test's results into an explicitly named file.
 */
public class NamedFileNamingConvention implements FileNamingConvention, TestSpecific<FileNamingConvention> {
    private final String approvedPath;
    private final String receivedPath;

    public NamedFileNamingConvention(String approvedPath, String receivedPath) {
        this.approvedPath = approvedPath;
        this.receivedPath = receivedPath;
    }

    public static TestSpecific<FileNamingConvention> forApprovedFile(String path) {
        return new NamedFileNamingConvention(path, appendBeforeExtension(path, "-received"));
    }

    public static TestSpecific<FileNamingConvention> forApprovedAndReceivedFiles(String approvedPath, String receivedPath) {
        return new NamedFileNamingConvention(approvedPath, receivedPath);
    }

    @Override
    public File approvedFile(String fileExtension) {
        return addExtensionIfNotAlreadySpecified(approvedPath, fileExtension);
    }

    @Override
    public File receivedFile(String fileExtension) {
        return addExtensionIfNotAlreadySpecified(receivedPath, fileExtension);
    }

    private File addExtensionIfNotAlreadySpecified(String path, String fileExtension) {
        if (Files.getFileExtension(path).isEmpty()) {
            return new File(path + fileExtension);
        }
        else {
            return new File(path);
        }
    }

    @Override
    public FileNamingConvention forTest(Class<?> testClass, String testName) {
        return this;
    }

    private static String appendBeforeExtension(String path, String suffix) {
        String extension = Files.getFileExtension(path);
        if (extension.isEmpty()) {
            return path + suffix;
        }
        else {
            return new File(new File(path).getParent(), Files.getNameWithoutExtension(path) + suffix + "." + extension).toString();
        }
    }
}
