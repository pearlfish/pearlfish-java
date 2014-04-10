package info.pearlfish.internal;

import info.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static info.pearlfish.formats.Formats.STRING;

public class IOTest {
    @Rule
    public ApprovalRule<String> approvals = InternalApprovals.selfTestApprover(STRING);

    @Test
    public void binaryToHexDump() throws IOException {
        byte[] bytes = new byte[1000];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }

        approvals.check(IO.toHexDump(new ByteArrayInputStream(bytes)));
    }
}
