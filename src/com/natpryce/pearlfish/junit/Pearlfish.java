package com.natpryce.pearlfish.junit;

import com.natpryce.pearlfish.formats.Format;
import com.natpryce.pearlfish.formats.Formats;
import com.natpryce.pearlfish.internal.PearlfishApproverFactory;
import org.rococoa.okeydoke.junit.ApprovalsRule;

public class Pearlfish implements Formats {
    public static ApprovalsRule approvalRule(final String srcRoot) {
        return new ApprovalsRule(new PearlfishApproverFactory(srcRoot, PLAIN_TEXT));
    }

    public static ApprovalsRule approvalRule(final String srcRoot, final Format format) {
        return new ApprovalsRule(new PearlfishApproverFactory(srcRoot, format));
    }
}
