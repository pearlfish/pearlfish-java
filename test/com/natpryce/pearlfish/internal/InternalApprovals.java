package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.naming.NextToSourceNamingConvention;

public class InternalApprovals {
    static <T> ApprovalRule<T> selfTestApprover(final TestSpecific<? extends Format<? super T>> format) {
        return new ApprovalRule<T>(format, new NextToSourceNamingConvention("test"));
    }
}
