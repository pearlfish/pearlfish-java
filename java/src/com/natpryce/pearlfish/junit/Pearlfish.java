package com.natpryce.pearlfish.junit;

import com.natpryce.pearlfish.TemplateFormatter;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.Sources;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.nio.charset.Charset;

public class Pearlfish {
    public static ApprovalsRule pearlfishApprovalsRule(final String srcRoot) {
        return new ApprovalsRule(new ApproverFactory() {
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(
                        testName,
                        Sources.in(srcRoot, testClass.getPackage()),
                        new TemplateFormatter(testClass, testName, Charset.defaultCharset()),
                        Reporters.reporter());
            }
        });
    }
}
