package eva;

public class EvaNil {
    public Object defaultValue() { return null; }

    @Override
    public String toString() { return "nil"; }

    public int toInt() { return 0; }

    public boolean toBoolean() { return false; }
}
