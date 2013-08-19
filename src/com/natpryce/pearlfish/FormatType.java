package com.natpryce.pearlfish;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class FormatType {
    private final String name;
    private final FormatType baseTypeOrNull;

    private FormatType(String name, FormatType baseTypeOrNull) {
        this.name = name;
        this.baseTypeOrNull = baseTypeOrNull;
    }

    public static FormatType of(String name, String ...moreNames) {
        FormatType type = new FormatType(name, null);
        for (String anotherName : moreNames) {
            type = new FormatType(anotherName, type);
        }
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormatType that = (FormatType) o;

        return !(baseTypeOrNull != null ? !baseTypeOrNull.equals(that.baseTypeOrNull) : that.baseTypeOrNull != null)
            && name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + (baseTypeOrNull != null ? baseTypeOrNull.hashCode() : 0);
    }

    public String toString() {
        List<String> parts = new ArrayList<String>(4);
        for (FormatType t = this; t != null; t = t.baseTypeOrNull) {
            parts.add(t.name);
        }

        return Joiner.on(".").join(Lists.reverse(parts));
    }

    public boolean canBeGeneralised() {
        return baseTypeOrNull != null;
    }

    public FormatType generalised() {
        return baseTypeOrNull;
    }

    public FormatType mostGeneralType() {
        FormatType t = this;

        while (t.baseTypeOrNull != null) {
            t = t.baseTypeOrNull;
        }

        return t;
    }
}
