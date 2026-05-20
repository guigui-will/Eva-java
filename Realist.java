package eva;

import com.sun.jna.Pointer;
import java.util.ArrayList;
import java.util.List;

public class EvaList {
    private final Pointer ptr;
    public final int length;

    public EvaList(Pointer ptr) {
        this.ptr    = ptr;
        this.length = Ffi.wrapper.eva_get_list_length_wrapper(ptr);
    }

    public Object get(int i) {
        if (i < 0 || i >= length) return new EvaNil();
        Pointer result = Ffi.wrapper.eva_get_list_field_wrapper(ptr, i);
        if (result == null) throw new RuntimeException("Failed to get value from list");
        return Values.makeValueFrom(result);
    }

    public List<Object> list() {
        List<Object> out = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
            out.add(get(i));
        return out;
    }

    @Override
    public String toString() {
        return "[Eva list of length " + length + "]";
    }
}
