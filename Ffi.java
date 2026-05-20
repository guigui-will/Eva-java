package eva;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

import java.io.File;

public class Ffi {

    public interface LibEva extends Library {
        Pointer eva_make_parser(byte[] path);
        boolean eva_check_exist_field_in_namespace(Pointer parser, byte[] ns, byte[] field);
    }

    public interface WrapperLib extends Library {
        void eva_wrapper_load_library(byte[] path);
        Pointer eva_get_value_from_namespace_wrapper(Pointer parser, byte[] ns, byte[] field);
        int eva_get_list_length_wrapper(Pointer list);
        Pointer eva_get_list_field_wrapper(Pointer list, int index);
        boolean eva_check_exist_field_in_map_wrapper(Pointer map, byte[] key);
        Pointer eva_get_map_field_wrapper(Pointer map, byte[] key);
        int eva_get_map_length_wrapper(Pointer map);
        Pointer eva_get_all_keys_from_map_wrapper(Pointer map);
    }

    public static final String libraryPath;
    public static final LibEva libEva;
    public static final WrapperLib wrapper;

    static {
        String os   = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        String platform = os.contains("win") ? "windows"
                        : os.contains("mac") ? "darwin"
                        : "linux";

        String suffix = os.contains("win") ? "dll"
                      : os.contains("mac") ? "dylib"
                      : "so";

        String base = new File(Ffi.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath())
                .getParentFile().getParent();

        libraryPath = base + "/eva/libeva-" + platform + "-" + arch + "." + suffix;

        libEva  = Native.load(libraryPath, LibEva.class);
        wrapper = Native.load(libraryPath, WrapperLib.class);
    }

    public static byte[] cstr(String s) {
        byte[] src = s.getBytes();
        byte[] out = new byte[src.length + 1];
        System.arraycopy(src, 0, out, 0, src.length);
        return out;
    }
}
