package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.TestSpecificFormat;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.naming.NextToSourceNamingConvention;

public class InternalApprovals {
    static <T> ApprovalRule<T> selfTestApprover(final TestSpecificFormat<? super T> format) {
        return new ApprovalRule<T>(format, new NextToSourceNamingConvention("test"));
    }
}
