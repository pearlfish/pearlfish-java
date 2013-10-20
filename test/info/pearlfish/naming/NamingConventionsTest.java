package info.pearlfish.naming;

import info.pearlfish.FileNamingConvention;
import info.pearlfish.TestSpecific;
import info.pearlfish.adaptor.junit.ApprovalRule;
import info.pearlfish.internal.InternalApprovals;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static info.pearlfish.formats.Formats.MARKDOWN;

public class NamingConventionsTest {
    public static final String TEST_NAME = "exampleTest";
    @Rule
    public ApprovalRule<Object> approval = InternalApprovals.selfTestApprover(MARKDOWN);

    @Test
    public void testNamingConventions() throws IOException {
        final Class<? extends NamingConventionsTest> theTestClass = getClass();

        approval.check(new Object() {
            public String testClass = theTestClass.getName();
            public String testMethod = TEST_NAME;
            public Object nextToSource = sample(
                    NextToSourceNamingConvention.forDirectory("example"), ".txt");
            public Object singleDir = sample(
                    SingleDirectoryNamingConvention.forDirectory("example"), ".txt");
            public Object fixedNameRoot = sample(
                    NamedFileNamingConvention.forApprovedFile("docs/example"), ".txt");
            public Object fixedNameRoots = sample(
                    NamedFileNamingConvention.forApprovedAndReceivedFiles("docs/approved-example", "docs/generated-example"), ".txt");
            public Object fixedNameWithExtension = sample(
                    NamedFileNamingConvention.forApprovedFile("docs/example.foo"), ".txt");
            public Object fixedNamesWithExtension = sample(
                    NamedFileNamingConvention.forApprovedAndReceivedFiles("docs/approved-example.foo", "docs/generated-example.foo"), ".txt");
        });
    }

    private Object sample(final TestSpecific<FileNamingConvention> tsNamingConvention, final String fileExtension) {
        final FileNamingConvention namingConvention = tsNamingConvention.forTest(getClass(), TEST_NAME);

        return new Object() {
            public String approvedFileName = namingConvention.approvedFile(fileExtension).getPath();
            public String receivedFileName = namingConvention.receivedFile(fileExtension).getPath();
        };
    }
}
