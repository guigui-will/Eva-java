package eva;

public enum ValueType {
    EVA_STRING(0),
    EVA_NUMBER(1),
    EVA_BOOL(2),
    EVA_MAP(3),
    EVA_LIST(4),
    EVA_NIL(5);

    public final int value;

    ValueType(int value) {
        this.value = value;
    }

    public static ValueType fromInt(int v) {
        for (ValueType t : values())
            if (t.value == v) return t;
        throw new IllegalArgumentException("Unknown tag: " + v);
    }
}
