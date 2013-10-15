package info.pearlfish.internal;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public class LineReader implements Lines, Closeable {
    private final BufferedReader reader;
    private String pushBack = null;

    public LineReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    @Override
    public String readLine() throws IOException {
        if (pushBack == null) {
            return reader.readLine();
        } else {
            String result = pushBack;
            pushBack = null;
            return result;
        }
    }

    @Override
    public void pushBack(String line) throws IOException {
        if (pushBack != null) {
            throw new IllegalStateException("only one line of pushback is allowed");
        }

        pushBack = line;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
