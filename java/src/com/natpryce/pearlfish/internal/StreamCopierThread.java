package com.natpryce.pearlfish.internal;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamCopierThread extends Thread {
    private OutputStream outputStream;
    private InputStream inputStream;

    public StreamCopierThread(final InputStream inputStream, final OutputStream outputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            ByteStreams.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
