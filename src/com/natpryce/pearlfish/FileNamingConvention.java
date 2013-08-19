package com.natpryce.pearlfish;

import java.io.File;

public interface FileNamingConvention {
    File approvedFileName(String fileExtension);

    File receivedFileName(String fileExtension);
}
