package com.natpryce.pearlfish.internal;

import com.google.common.base.Joiner;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;
import java.util.LinkedHashMap;

public class MarkdownEscapingTest {
    public @Rule ApprovalsRule approvals = ApprovalsRule.fileSystemRule("test");

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
            transforms.put(example, escaping.escape(example));
        }

        approvals.assertApproved(Joiner.on("\n").withKeyValueSeparator(" -> ").join(transforms));
    }
}
