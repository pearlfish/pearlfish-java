package com.natpryce.pearlfish.unstable.prolog;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class PrologFormat implements Format<FactBase> {
    public static final TestSpecific<PrologFormat> PROLOG = new TestSpecific<PrologFormat>() {
        @Override
        public PrologFormat forTest(Class<?> testClass, String testName) {
            return new PrologFormat();
        }
    };

    @Override
    public void write(FactBase facts, OutputStream output) throws IOException {
        PrintWriter writer = new PrintWriter(output);
        facts.writePrologSyntax(writer);
        writer.flush();
    }

    @Override
    public String fileExtension() {
        return ".pl";
    }

    @Override
    public FormatType fileType() {
        return FormatType.of("text", "prolog");
    }
}
