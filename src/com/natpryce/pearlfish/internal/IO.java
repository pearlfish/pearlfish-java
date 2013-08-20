package com.natpryce.pearlfish.internal;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.io.ByteStreams;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IO {
    public static String toHexDump(File file) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        try {
            return toHexDump(in);
        } finally {
            in.close();
        }
    }

    public static String toHexDump(InputStream in) throws IOException {
        return Joiner.on("\n").join(Splitter.fixedLength(128).split(
                DatatypeConverter.printHexBinary(ByteStreams.toByteArray(in))));
    }
}
