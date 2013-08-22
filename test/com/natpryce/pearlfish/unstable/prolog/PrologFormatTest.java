package com.natpryce.pearlfish.unstable.prolog;

import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import com.natpryce.pearlfish.internal.InternalApprovals;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.natpryce.pearlfish.unstable.prolog.PrologFormat.PROLOG;

public class PrologFormatTest {
    public
    @Rule
    ApprovalRule<FactBase> approval = InternalApprovals.selfTestApprover(PROLOG);

    @Test
    public void generatesProlog() throws IOException {
        FactBase facts = new FactBase();

        Facts female = facts.newFact("female", "person");
        female.declare(true, "alice");
        female.declare(false, "bob");
        female.declare(true, "carol");
        female.declare(false, "dave");
        female.declare(true, "eve");

        Facts greater = facts.newFact("ordered", "number", "number");
        greater.declare(true, 10, 20);
        greater.declare(true, 1, 2);
        greater.declare(true, -5, -3);
        greater.declare(true, -5, 4);
        greater.declare(false, 2, 1);
        greater.declare(false, 0, 0);
        greater.declare(false, 1, 1);

        approval.check(facts);
    }
}
