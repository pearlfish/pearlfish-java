package com.natpryce.pearlfish.internal;

import com.google.common.base.Joiner;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static com.natpryce.pearlfish.formats.Formats.STRING;

public class MarkdownEscapingTest {
    public @Rule ApprovalRule<String> approvals = InternalApprovals.selfTestApprover(STRING);

    MarkdownEscaping escaping = new MarkdownEscaping();

    @Test
    public void escaping() throws IOException {
        LinkedHashMap<String,String> transforms = new LinkedHashMap<String, String>();

        String[] examples = {
            "no escaping",
            "*asterisks*",
            "_underscores_",
            "|pipes|",
            "+pluses+",
            "-minuses-"
        };

        for (String example : examples) {
            transforms.put(example, escaping.filter(example));
        }

        approvals.check(Joiner.on("\n").withKeyValueSeparator(" -> ").join(transforms));
    }
}
