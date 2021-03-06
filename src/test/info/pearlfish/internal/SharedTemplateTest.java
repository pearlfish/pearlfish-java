package info.pearlfish.internal;

import info.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static info.pearlfish.formats.TemplateFormats.PLAIN_TEXT;
import static info.pearlfish.internal.InternalApprovals.selfTestApprover;

public class SharedTemplateTest {
    @Rule
    public ApprovalRule<Object> approval = selfTestApprover(PLAIN_TEXT.withTemplate(getClass().getSimpleName() + ".template"));

    @Test
    public void firstTestWithSharedTemplate() throws IOException {
        approval.check(new Object() {
            String name = "alice";
            int age = 40;
        });
    }

    @Test
    public void secondTestWithSharedTemplate() throws IOException {
        approval.check(new Object() {
            String name = "bob";
            int age = 32;
        });
    }
}
