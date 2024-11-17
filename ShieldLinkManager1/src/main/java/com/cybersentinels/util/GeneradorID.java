package com.cybersentinels.util;

import java.util.UUID;

public class GeneradorID {
    public static String generarUUID() {
        return UUID.randomUUID().toString();
    }
}