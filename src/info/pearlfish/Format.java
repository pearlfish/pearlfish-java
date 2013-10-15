package info.pearlfish;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A format in which to record test results of type <var>T</var>.
 *
 * A Format writes a value of type <var>T</var> to a byte stream. It reports the file extension
 * that should be used for files in this format and the {@link FormatType} of the format, which
 * is used to select an appropriate {@link DifferenceReporter}.
 *
 * @param <T> the type of object that can be formatted by this Format.
 */
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
    String fileExtension();

    /**
     * Reports the format type files in this format.
     *
     * This is used to look up a suitable method for reporting differences between the files.
     *
     * @return the format type of files in this format.
     */
    FormatType fileType();
}
