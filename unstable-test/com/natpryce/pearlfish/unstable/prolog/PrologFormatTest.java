package com.natpryce.pearlfish.unstable.prolog;

import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.natpryce.pearlfish.internal.InternalApprovals.unstableSelfTestApprover;
import static com.natpryce.pearlfish.unstable.prolog.Fact.ANYTHING;
import static com.natpryce.pearlfish.unstable.prolog.PrologFormat.PROLOG;

public class PrologFormatTest {
    public
    @Rule
    ApprovalRule<FactBase> approval = unstableSelfTestApprover(PROLOG);

    @Test
    public void generatesProlog() throws IOException {
        FactBase facts = new FactBase();

        Relation female = facts.newFact("female", "person");
        female.declare(true, "Alice");
        female.declare(false, "Bbob");
        female.declare(true, "Carol");
        female.declare(false, "Dave");
        female.declare(true, "Eve");

        Relation greater = facts.newFact("ordered", "number", "number");
        greater.declare(true, 10, 20);
        greater.declare(true, 1, 2);
        greater.declare(true, -5, -3);
        greater.declare(true, -5, 4);
        greater.declare(false, 2, 1);
        greater.declare(false, 0, 0);
        greater.declare(false, 1, 1);

        Relation lightOn = facts.newFact("light_level", "room", "level");
        lightOn.declare(true, "kitchen", "level_1");
        lightOn.declare(true, "livingRoom", "level_4");
        lightOn.declare(true, ANYTHING, "level_0");

        approval.check(facts);
    }
}
