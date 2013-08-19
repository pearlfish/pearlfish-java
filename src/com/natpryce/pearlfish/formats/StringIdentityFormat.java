package com.natpryce.pearlfish.formats;

import com.natpryce.pearlfish.FormatType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class StringIdentityFormat implements com.natpryce.pearlfish.Format<String> {
    private final String extension;
    private final FormatType fileType;

    public StringIdentityFormat(String extension, FormatType fileType) {
        this.extension = extension;
        this.fileType = fileType;
    }

    @Override
    public void write(String value, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(value);
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
