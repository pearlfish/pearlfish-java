package com.natpryce.pearlfish;

import java.io.File;

/**
 * An object that knows where approved and received files for a test are located and how they are named.
 */
public interface FileNamingConvention {
    /**
     * Returns the approved file for a test.
     *
     * @param fileExtension the file extension to use for the file, including any separator character (e.g. ".txt").
     * @return the identifier of the approved file.
     */
    File approvedFile(String fileExtension);

    /**
     * Returns the received file for a test.
     *
     * @param fileExtension the file extension to use for the file, including any separator character (e.g. ".txt").
     * @return the identifier of the received file.
     */
    File receivedFile(String fileExtension);
}
