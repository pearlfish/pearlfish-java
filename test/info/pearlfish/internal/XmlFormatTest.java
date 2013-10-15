package info.pearlfish.internal;

import info.pearlfish.adaptor.junit.ApprovalRule;
import info.pearlfish.formats.Formats;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class XmlFormatTest {
    @Rule
    public ApprovalRule<Object> approval = InternalApprovals.selfTestApprover(Formats.XML);

    @Test
    public void writeXml() throws IOException {
        approval.check(new Object() {
            public String name = "Bob O'Reilly";
            public int age = 30;
            public String gender = "male";
        });
    }
}
