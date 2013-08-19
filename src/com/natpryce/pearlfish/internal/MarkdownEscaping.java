package com.natpryce.pearlfish.internal;

public class MarkdownEscaping extends PrefixedEscaping {
    public MarkdownEscaping() {
        super('\\', '`', '*', '_', '{', '}', '[', ']', '(', ')', '#', '+', '-', '!', '|');
    }
}
