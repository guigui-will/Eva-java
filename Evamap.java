package eva;

import com.sun.jna.Pointer;

public class EvaMap {
    private final Pointer ptr;
    public final int length;

    public EvaMap(Pointer ptr) {
        this.ptr    = ptr;
        this.length = Ffi.wrapper.eva_get_map_length_wrapper(ptr);
    }

    public EvaList keys() {
        Pointer result = Ffi.wrapper.eva_get_all_keys_from_map_wrapper(ptr);
        if (result == null) throw new RuntimeException("Failed to get keys from map");
        return new EvaList(result);
    }

    public Object get(String key) {
        byte[] k = Ffi.cstr(key);
        if (!Ffi.wrapper.eva_check_exist_field_in_map_wrapper(ptr, k))
            return new EvaNil();
        Pointer result = Ffi.wrapper.eva_get_map_field_wrapper(ptr, k);
        if (result == null) throw new RuntimeException("Failed to get value from map");
        return Values.makeValueFrom(result);
    }

    @Override
    public String toString() {
        return "[Eva map of length " + length + "]";
    }
}
