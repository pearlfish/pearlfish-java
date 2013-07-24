package com.natpryce.pearlfish.junit;

import com.natpryce.pearlfish.formats.Format;
import com.natpryce.pearlfish.formats.Formats;
import com.natpryce.pearlfish.formats.MissingTemplateException;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;

import java.io.File;

public class Pearlfish implements Formats {
    public static ApprovalsRule approvalRule(final String srcRoot, final Format format) {
        return new ApprovalsRule(new PearlfishApproverFactory(srcRoot, format));
    }

    private static class PearlfishApproverFactory implements ApproverFactory {
        private final String srcRoot;
        private final Format preferredFormat;

        public PearlfishApproverFactory(String srcRoot, Format preferredFormat) {
            this.srcRoot = srcRoot;
            this.preferredFormat = preferredFormat;
        }

        public Approver create(String testName, Class<?> testClass) {
            Formatter<Object,String> formatter;
            String fileExtension;
            try {
                formatter = preferredFormat.formatterFor(testName, testClass);
                fileExtension = preferredFormat.fileExtension();
            }
            catch (MissingTemplateException e) {
                formatter = YAML.formatterFor(testName, testClass);
                fileExtension = YAML.fileExtension();
            }

            return new Approver(
                    testName,
                    new FileSystemSourceOfApproval(
                            new File(srcRoot), testClass.getPackage()).
                            withTypeExtension(fileExtension),
                    formatter,
                    Reporters.reporter());
        }
    }
}
