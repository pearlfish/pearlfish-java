package info.pearlfish;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An identifier of a file format, used to determine an appropriate method of reporting differences
 * between files in this format.
 */
public class FormatType {
    /**
     * Constant defining the commonly used "text" base-type.
     */
    public static final FormatType TEXT = FormatType.of("text");

    /**
     * Constant defining the commonly used "json" base-type.
     */
    public static final FormatType JSON = TEXT.specialised("json");

    /**
     * Constant defining the commonly used "xml" base-type.
     */
    public static final FormatType XML = TEXT.specialised("xml");


    private final String name;
    private final FormatType baseTypeOrNull;

    private FormatType(String name, FormatType baseTypeOrNull) {
        assert !name.isEmpty();

        this.name = name;
        this.baseTypeOrNull = baseTypeOrNull;
    }

    public static FormatType of(String name, String ...moreNames) {
        return new FormatType(name, null).specialised(moreNames);
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

    public FormatType specialised(String name, String ... moreNames) {
        return new FormatType(name, this).specialised(moreNames);
    }

    public boolean canBeGeneralised() {
        return baseTypeOrNull != null;
    }

    public FormatType generalised() {
        return baseTypeOrNull;
    }

    private FormatType specialised(String[] moreNames) {
        FormatType result = this;
        for (String anotherName : moreNames) {
            result = new FormatType(anotherName, result);
        }
        return result;
    }

    public static FormatType valueOf(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("invalid format type syntax: empty string");
        }

        String[] parts = s.split("\\.");
        return FormatType.of(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
    }
}
