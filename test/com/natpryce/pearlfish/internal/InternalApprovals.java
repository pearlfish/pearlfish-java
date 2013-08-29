package com.natpryce.pearlfish.internal;

import com.natpryce.pearlfish.Format;
import com.natpryce.pearlfish.TestSpecific;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.naming.NextToSourceNamingConvention;

public class InternalApprovals {
    public static <T> ApprovalRule<T> selfTestApprover(TestSpecific<? extends Format<? super T>> format) {
        return new ApprovalRule<T>(format, NextToSourceNamingConvention.forSourceDirectory("test"));
    }

    public static <T> ApprovalRule<T> unstableSelfTestApprover(TestSpecific<? extends Format<? super T>> format) {
        return new ApprovalRule<T>(format, NextToSourceNamingConvention.forSourceDirectory("unstable-test"));
    }
}
