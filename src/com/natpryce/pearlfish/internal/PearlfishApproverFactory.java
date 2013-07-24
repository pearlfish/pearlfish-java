package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.formats.Format;
import com.natpryce.pearlfish.formats.Formats;
import com.natpryce.pearlfish.formats.MissingTemplateException;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;

import java.io.File;

public class PearlfishApproverFactory implements ApproverFactory {
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
            formatter = Formats.YAML.formatterFor(testName, testClass);
            fileExtension = Formats.YAML.fileExtension();
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
