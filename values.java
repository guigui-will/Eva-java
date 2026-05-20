package eva;

import com.sun.jna.Pointer;

public class Values {
    public static Object makeValueFrom(Pointer ptr) {
        EvaValue val = new EvaValue(ptr);
        switch (val.tag()) {
            case EVA_BOOL:   return val.asBool();
            case EVA_NUMBER: return val.asNumber();
            case EVA_NIL:    return new EvaNil();
            case EVA_STRING: return val.asString();
            case EVA_LIST:   return new EvaList(ptr);
            case EVA_MAP:    return new EvaMap(ptr);
            default: throw new IllegalStateException("Unknown EVA tag: " + val.tag());
        }
    }
}
