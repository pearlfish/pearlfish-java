package info.pearlfish.naming;

import info.pearlfish.FileNamingConvention;
import info.pearlfish.adaptor.junit.ApprovalRule;
import info.pearlfish.internal.InternalApprovals;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static info.pearlfish.formats.Formats.MARKDOWN;

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
            public Object fixedNameRoot = sample(
                    new NamedFileNamingConvention("docs/example"), ".txt");
            public Object fixedNameRoots = sample(
                    new NamedFileNamingConvention("docs/approved-example", "docs/generated-example"), ".txt");
            public Object fixedNameWithExtension = sample(
                    new NamedFileNamingConvention("docs/example.foo"), ".txt");
            public Object fixedNamesWithExtension = sample(
                    new NamedFileNamingConvention("docs/approved-example.foo", "docs/generated-example.foo"), ".txt");
        });
    }

    private static Object sample(final FileNamingConvention namingConvention, final String fileExtension) {
        return new Object() {
            public String approvedFileName = namingConvention.approvedFile(fileExtension).getPath();
            public String receivedFileName = namingConvention.receivedFile(fileExtension).getPath();
        };
    }
}
