package com.natpryce.pearlfish.junit;

import com.natpryce.pearlfish.formats.Format;
import com.natpryce.pearlfish.formats.Formats;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;

import java.io.File;

public class Pearlfish implements Formats {
    public static ApprovalsRule approvalRule(final String srcRoot, final Format format) {
        return new ApprovalsRule(new ApproverFactory() {
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(
                        testName,
                        new FileSystemSourceOfApproval(
                                new File(srcRoot), testClass.getPackage()).
                                withTypeExtension(format.fileExtension()),
                        format.formatterFor(testName, testClass),
                        Reporters.reporter());
            }
        });
    }
}
