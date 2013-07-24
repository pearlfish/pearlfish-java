package com.natpryce.pearlfish.junit;

import com.natpryce.pearlfish.formats.Format;
import com.natpryce.pearlfish.formats.Formats;
import com.natpryce.pearlfish.formats.MarkdownFormatter;
import com.natpryce.pearlfish.formats.TemplateFormatter;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;

import java.io.File;
import java.nio.charset.Charset;

public class Pearlfish implements Formats {
    public static ApprovalsRule pearlfishApprovalsRule(final String srcRoot, final Format format) {
        return new ApprovalsRule(new ApproverFactory() {
            public Approver create(String testName, Class<?> testClass) {
                TemplateFormatter formatter = format.formatterFor(testName, testClass);

                FileSystemSourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(
                        new File(srcRoot), testClass.getPackage()).
                        withTypeExtension(formatter.fileExtension());

                return new Approver(testName, sourceOfApproval, formatter, Reporters.reporter());
            }
        });
    }
}
