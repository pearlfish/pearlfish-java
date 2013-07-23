package com.natpryce.pearlfish.junit;

import com.natpryce.pearlfish.formats.MarkdownFormatter;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;

import java.io.File;
import java.nio.charset.Charset;

public class Pearlfish {
    public static ApprovalsRule pearlfishApprovalsRule(final String srcRoot) {
        return new ApprovalsRule(new ApproverFactory() {
            public Approver create(String testName, Class<?> testClass) {
                MarkdownFormatter formatter = new MarkdownFormatter(testClass, testName, Charset.defaultCharset());

                FileSystemSourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(
                        new File(srcRoot), testClass.getPackage()).
                        withTypeExtension(formatter.fileExtension());

                return new Approver(testName, sourceOfApproval, formatter, Reporters.reporter());
            }
        });
    }
}
