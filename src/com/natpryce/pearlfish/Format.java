package com.natpryce.pearlfish;

import java.io.IOException;
import java.io.OutputStream;

public interface Format<T> {
    /**
     * Writes a formatted representation of <var>value</var> to <var>output</var>.
     *
     * @param value  The value to be formatted
     * @param output The output stream into which to write the formatted representation
     * @throws IOException if output fails
     */
    void write(T value, OutputStream output) throws IOException;

    /**
     * Reports the file extension of files in this format.
     * This includes the initial "." of the file extension. For example, ".txt" for text files,
     * ".csv" for CSV files, ".png" for PNG files.
     *
     * @return the file extension of files in this format.
     */
    String extension();
}
