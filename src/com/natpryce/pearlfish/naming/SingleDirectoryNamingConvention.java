package com.natpryce.pearlfish.naming;

import com.natpryce.pearlfish.FileNamingConvention;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.internal.PerTestFileNamingConvention;

import java.io.File;

public class SingleDirectoryNamingConvention extends PerTestFileNamingConvention {
    public SingleDirectoryNamingConvention(File dir, Class<?> testClass, String testName) {
        super(dir, testClass, testName);
    }

    public static TestSpecific<FileNamingConvention> forDirectory(String sourceDirName) {
        return forDirectory(new File(sourceDirName));
    }

    public static TestSpecific<FileNamingConvention> forDirectory(final File sourceDir) {
        return new TestSpecific<FileNamingConvention>() {
            @Override
            public FileNamingConvention forTest(Class<?> testClass, String testName) {
                return new SingleDirectoryNamingConvention(sourceDir, testClass, testName);
            }
        };
    }

    @Override
    protected File fileFor(File directory, Class<?> testClass, String testName, String type, String fileExtension) {
        return new File(directory, testClass.getName() + "." + testName + "-" + type + fileExtension);
    }
}
