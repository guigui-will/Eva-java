package eva;

import java.util.Map;
import java.util.function.Function;

public class Errors {
    public static final Map<Integer, Function<Eva, String>> errors = Map.of(
        1, self -> "Can't open file: " + self.getFilename()
    );
}
