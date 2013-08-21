package com.natpryce.pearlfish.unstable.prolog;

import com.google.common.collect.ImmutableList;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.internal.InternalApprovals;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.natpryce.pearlfish.unstable.prolog.PrologFormat.PROLOG;
import static java.util.Arrays.asList;

public class PrologFormatTest {
    public
    @Rule
    ApprovalRule<Iterable<Facts>> approval = InternalApprovals.selfTestApprover(PROLOG);

    @Test
    public void generatesProlog() throws IOException {
        final List<Fact> facts = asList(
                new Fact(true, "alice"),
                new Fact(false, "bob"),
                new Fact(true, "carol"),
                new Fact(false, "dave"),
                new Fact(true, "eve"));

        final List<ImmutableList<String>> types = asList(
                ImmutableList.of("+person"));

        approval.check(asList(new Facts("female", types, facts)));
    }
}
