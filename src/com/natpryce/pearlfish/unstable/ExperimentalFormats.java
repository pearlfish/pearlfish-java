package com.natpryce.pearlfish.unstable;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.FormatType;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.formats.Formats;
import com.natpryce.pearlfish.formats.TemplatedTextFormat;
import com.natpryce.pearlfish.formats.TextFilter;
import com.natpryce.pearlfish.internal.PrologEscaping;

import java.nio.charset.Charset;

public class ExperimentalFormats extends Formats {
    public static final TestSpecific<TemplatedTextFormat> _PROLOG = new TestSpecific<TemplatedTextFormat>() {
        @Override
        public TemplatedTextFormat forTest(Class<?> testClass, String testName) {
            return TemplatedTextFormat.fromResource(testClass, testName, Charset.defaultCharset(), new PrologEscaping(), TextFilter.IDENTITY, ".pl", FormatType.of("text", "prolog"));
        }
    };

    /**
     * Generate Prolog fact bases from templates
     */
    public static final TestSpecific<Format<Object>> PROLOG = fallingBackTo(YAML, ExperimentalFormats._PROLOG);
}
