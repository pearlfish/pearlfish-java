package com.natpryce.pearlfish.formats;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class StringIdentityFormat implements com.natpryce.pearlfish.Format<String> {
    private final String extension;

    public StringIdentityFormat() {
        this(".txt");
    }

    public StringIdentityFormat(String extension) {
        this.extension = extension;
    }

    @Override
    public void write(String value, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(value);
        writer.flush();
    }

    @Override
    public String extension() {
        return extension;
    }
}
