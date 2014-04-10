package info.pearlfish.internal;

import info.pearlfish.Format;
import info.pearlfish.FormatType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ToStringFormat implements Format<Object> {
    private final String extension;
    private final FormatType fileType;

    public ToStringFormat(String extension, FormatType fileType) {
        this.extension = extension;
        this.fileType = fileType;
    }

    @Override
    public void write(Object value, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(value.toString());
        writer.flush();
    }

    @Override
    public String fileExtension() {
        return extension;
    }

    @Override
    public FormatType fileType() {
        return fileType;
    }
}
