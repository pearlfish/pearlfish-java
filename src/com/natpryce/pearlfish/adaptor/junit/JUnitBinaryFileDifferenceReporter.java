package com.natpryce.pearlfish.adaptor.junit;

import com.natpryce.pearlfish.internal.IO;

import java.io.File;
import java.io.IOException;

public class JUnitBinaryFileDifferenceReporter extends JUnitDifferenceReporter {
    @Override
    protected String loadContents(File file) throws IOException {
        return IO.toHexDump(file);
    }
}
