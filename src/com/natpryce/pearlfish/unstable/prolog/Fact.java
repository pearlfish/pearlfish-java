package com.natpryce.pearlfish.unstable.prolog;

public class Fact {
    public final boolean isTrue;
    public final Object[] params;

    public Fact(boolean isTrue, Object... params) {
        this.isTrue = isTrue;
        this.params = params;
    }
}
