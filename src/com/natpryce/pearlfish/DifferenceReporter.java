package com.natpryce.pearlfish;

import java.io.File;
import java.io.IOException;

/**
 * Implemented by objects that can report the difference between approved and received files.
 */
public interface DifferenceReporter {
    /**
     * Reports the difference between the approved and received files.
     *
     * The approved file may not exist. That is, calling {@link File#exists()} on <var>approvedFile</var>
     * may return <code>false</code>.
     *
     * @param formatType identifies the format of the files
     * @param approvedFile the approved file. This file may not exist.
     * @param receivedFile the received file. This file always exists.
     * @throws IOException if reading the files or writing the report fails.
     */
    void reportDifference(FormatType formatType, File approvedFile, File receivedFile) throws IOException;
}
