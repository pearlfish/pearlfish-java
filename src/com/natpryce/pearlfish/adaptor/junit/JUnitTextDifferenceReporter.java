package com.natpryce.pearlfish.adaptor.junit;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class JUnitTextDifferenceReporter extends JUnitDifferenceReporter {
    @Override
    protected String loadContents(File file) throws IOException {
        return Files.toString(file, Charset.defaultCharset());
    }
}
