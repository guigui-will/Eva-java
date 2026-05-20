package eva;

import com.sun.jna.Pointer;
import java.io.File;

public class Eva {
    private final String path;
    private final String filename;
    private final Pointer driver;

    public Eva(String filepath) {
        this.path     = filepath;
        this.filename = new File(filepath).getName();

        Ffi.wrapper.eva_wrapper_load_library(Ffi.cstr(Ffi.libraryPath));

        this.driver = Ffi.libEva.eva_make_parser(Ffi.cstr(filepath));
        if (this.driver == null)
            throw new RuntimeException("Failed to spawn the Eva parser");

        long status = driver.getLong(0);
        if (status != 0) {
            String msg = Errors.errors.containsKey((int) status)
                ? Errors.errors.get((int) status).apply(this)
                : "Unknown error";
            throw new RuntimeException(msg);
        }
    }

    public Object get(String namespace, String field) {
        boolean exists = Ffi.libEva.eva_check_exist_field_in_namespace(
            driver, Ffi.cstr(namespace), Ffi.cstr(field)
        );
        if (!exists)
            throw new IllegalArgumentException(
                "Field '" + field + "' does not exist in namespace '" + namespace + "'"
            );

        Pointer result = Ffi.wrapper.eva_get_value_from_namespace_wrapper(
            driver, Ffi.cstr(namespace), Ffi.cstr(field)
        );
        if (result == null)
            throw new RuntimeException("Failed to get value from namespace");

        return Values.makeValueFrom(result);
    }

    public String getFilename() { return filename; }
}
