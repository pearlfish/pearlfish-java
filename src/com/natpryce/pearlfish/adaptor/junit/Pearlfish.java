package com.natpryce.pearlfish.adaptor.junit;

import com.natpryce.pearlfish.TestSpecificFormat;
import com.natpryce.pearlfish.formats.Formats;
import com.natpryce.pearlfish.naming.NextToSourceNamingConvention;

import java.io.File;

public class Pearlfish {
    public static <T> ApprovalRule<T> approvalRule(final String srcRoot) {
        return approvalRule(srcRoot, Formats.PLAIN_TEXT);
    }

    public static <T> ApprovalRule<T> approvalRule(final String srcRoot, final TestSpecificFormat<? super T> format) {
        return new ApprovalRule<T>(format, new NextToSourceNamingConvention(new File(srcRoot)));
    }
}
