package com.natpryce.pearlfish.internal;

public class XmlEscaping extends ReplaceEscaping {
    public XmlEscaping() {
        super(new String[][]{
                {"&", "&amp;"},
                {"\"", "&quot;"},
                {"<", "&lt;"},
                {">", "&gt;"},
        });
    }
}
