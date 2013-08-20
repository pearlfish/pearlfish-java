package com.natpryce.pearlfish;

import java.io.File;
import java.io.IOException;

public interface DifferenceReporter {
    void reportDifference(FormatType formatType, File approvedFile, File receivedFile) throws IOException;
}
