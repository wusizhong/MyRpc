package com.wsz.register;

import java.util.HashMap;
import java.util.Map;

public class LocalRegister {
    private static Map<String, Class> map = new HashMap<>();

    public static void register(String interfaceName, Class impl) {
        map.put(interfaceName, impl);
    }

    public static Class get(String interfaceName) {
        return map.get(interfaceName);
    }
}
