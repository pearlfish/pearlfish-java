package com.natpryce.pearlfish;

import java.io.File;

public interface FileNamingConvention {
    File approvedFileFor(Class<?> testClass, String testName, String fileExtension);

    File receivedFileFor(Class<?> testClass, String testName, String fileExtension);
}
