package com.natpryce.pearlfish.naming;

import com.natpryce.pearlfish.FileNamingConvention;

import java.io.File;

public class NextToSourceNamingConvention implements FileNamingConvention {
    private final File sourceDir;

    public NextToSourceNamingConvention(String sourceDirName) {
        this(new File(sourceDirName));
    }

    public NextToSourceNamingConvention(File sourceDir) {
        assert sourceDir.isDirectory();

        this.sourceDir = sourceDir;
    }

    @Override
    public File approvedFileFor(Class<?> testClass, String testName, String fileExtension) {
        return fileFor(testClass, testName, "approved", fileExtension);
    }

    @Override
    public File receivedFileFor(Class<?> testClass, String testName, String fileExtension) {
        return fileFor(testClass, testName, "received", fileExtension);
    }

    private File fileFor(Class<?> testClass, String testName, String type, String fileExtension) {
        return new File(sourceDir, testClass.getName().replace(".", File.separator) + "." + testName + "-" + type + fileExtension);
    }
}
