package info.pearlfish.internal;

import info.pearlfish.Format;
import info.pearlfish.TestSpecific;
import info.pearlfish.adaptor.junit.ApprovalRule;
import info.pearlfish.naming.NextToSourceNamingConvention;

public class InternalApprovals {
    public static <T> ApprovalRule<T> selfTestApprover(TestSpecific<? extends Format<? super T>> format) {
        return new ApprovalRule<T>(format, NextToSourceNamingConvention.forDirectory("src/test"));
    }
}
