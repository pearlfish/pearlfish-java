package com.natpryce.pearlfish.internal;

import java.io.IOException;

public interface Lines {
    String readLine() throws IOException;
    void pushBack(String line) throws IOException;
}
