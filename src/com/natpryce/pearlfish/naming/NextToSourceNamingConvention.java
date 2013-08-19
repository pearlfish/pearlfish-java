package com.natpryce.pearlfish.naming;

import com.natpryce.pearlfish.FileNamingConvention;
import com.natpryce.pearlfish.TestSpecific;

import java.io.File;

public class NextToSourceNamingConvention implements FileNamingConvention {
    private final File sourceDir;
    private final Class<?> testClass;
    private final String testName;

    public NextToSourceNamingConvention(File sourceDir, Class<?> testClass, String testName) {
        this.sourceDir = sourceDir;
        this.testClass = testClass;
        this.testName = testName;
    }

    @Override
    public File approvedFileName(String fileExtension) {
        return fileFor("approved", fileExtension);
    }

    @Override
    public File receivedFileName(String fileExtension) {
        return fileFor("received", fileExtension);
    }

    private File fileFor(String type, String fileExtension) {
        return new File(sourceDir, testClass.getName().replace(".", File.separator) + "." + testName + "-" + type + fileExtension);
    }

    public static TestSpecific<FileNamingConvention> forSourceDirectory(String sourceDirName) {
        return forSourceDirectory(new File(sourceDirName));
    }

    public static TestSpecific<FileNamingConvention> forSourceDirectory(final File sourceDir) {
        return new TestSpecific<FileNamingConvention>() {
            @Override
            public FileNamingConvention forTest(Class<?> testClass, String testName) {
                return new NextToSourceNamingConvention(sourceDir, testClass, testName);
            }
        };
    }
}
