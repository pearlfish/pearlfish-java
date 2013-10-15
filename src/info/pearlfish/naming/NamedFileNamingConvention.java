package info.pearlfish.naming;

import info.pearlfish.FileNamingConvention;
import info.pearlfish.TestSpecific;

import java.io.File;

public class NamedFileNamingConvention implements FileNamingConvention, TestSpecific<FileNamingConvention> {
    private final String approvedPath;
    private final String receivedPath;

    public NamedFileNamingConvention(String path) {
        this(path, path + "-received");
    }

    public NamedFileNamingConvention(String approvedPath, String receivedPath) {
        this.approvedPath = approvedPath;
        this.receivedPath = receivedPath;
    }

    @Override
    public File approvedFile(String fileExtension) {
        return new File(approvedPath + fileExtension);
    }

    @Override
    public File receivedFile(String fileExtension) {
        return new File(receivedPath + fileExtension);
    }

    @Override
    public FileNamingConvention forTest(Class<?> testClass, String testName) {
        return this;
    }
}
