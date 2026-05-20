package eva;

import com.sun.jna.Pointer;

public class EvaValue {
    private final Pointer ptr;

    public EvaValue(Pointer ptr) {
        this.ptr = ptr;
    }

    public ValueType tag() {
        return ValueType.fromInt(ptr.getInt(0));
    }

    public boolean asBool() {
        return ptr.getByte(8) != 0;
    }

    public double asNumber() {
        return ptr.getDouble(8);
    }

    public String asString() {
        long strPtr = ptr.getLong(8);
        if (strPtr == 0) return "";
        return new Pointer(strPtr).getString(0);
    }

    public Pointer raw() {
        return ptr;
    }
}
