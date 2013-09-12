package com.natpryce.pearlfish.naming;

import com.natpryce.pearlfish.FileNamingConvention;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.internal.InternalApprovals;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.natpryce.pearlfish.formats.Formats.MARKDOWN;

public class NamingConventionsTest {
    @Rule
    public ApprovalRule<Object> approval = InternalApprovals.selfTestApprover(MARKDOWN);

    @Test
    public void testNamingConventions() throws IOException {
        final Class<? extends NamingConventionsTest> testClass = getClass();

        approval.check(new Object() {
            public Object nextToSource = sample(
                    NextToSourceNamingConvention.forDirectory("example").forTest(testClass, "exampleTest"), ".txt");
            public Object singleDir = sample(
                    SingleDirectoryNamingConvention.forDirectory("example").forTest(testClass, "exampleTest"), ".txt");
            public Object fixedName = sample(
                    new NamedFileNamingConvention("docs/example"), ".txt");
        });
    }

    private static Object sample(final FileNamingConvention namingConvention, final String fileExtension) {
        return new Object() {
            public String approvedFileName = namingConvention.approvedFile(fileExtension).getPath();
            public String receivedFileName = namingConvention.receivedFile(fileExtension).getPath();
        };
    }
}
