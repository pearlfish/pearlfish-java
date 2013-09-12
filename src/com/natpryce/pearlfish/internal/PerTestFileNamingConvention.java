package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.FileNamingConvention;

import java.io.File;

public abstract class PerTestFileNamingConvention implements FileNamingConvention {
    protected final File dir;
    protected final Class<?> testClass;
    protected final String testName;

    public PerTestFileNamingConvention(File dir, Class<?> testClass, String testName) {
        this.testName = testName;
        this.testClass = testClass;
        this.dir = dir;
    }

    @Override
    public File approvedFile(String fileExtension) {
        return fileFor(dir, testClass, testName, "approved", fileExtension);
    }

    @Override
    public File receivedFile(String fileExtension) {
        return fileFor(dir, testClass, testName, "received", fileExtension);
    }

    protected abstract File fileFor(final File directory, final Class<?> testClass, final String testName, String type, String fileExtension);
}
