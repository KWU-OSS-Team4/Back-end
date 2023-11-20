package com.witheat.WithEatServer.common;

import java.util.Random;

public class KeyGenerator {
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rand = new Random();

        for (int i = 0; i < 6 ; i++) {
            key.append((rand.nextInt(10)));
        }

        return key.toString();
    }
}
