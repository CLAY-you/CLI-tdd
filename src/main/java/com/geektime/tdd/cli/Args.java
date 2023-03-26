package com.geektime.tdd.cli;

import java.lang.reflect.Constructor;

public class Args {
    public static <T> T parse(Class<T> optionType, String... args) {
        Constructor<?> constructor = optionType.getDeclaredConstructors()[0];
        try {
            return (T) constructor.newInstance(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
