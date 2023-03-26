package com.geektime.tdd.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionType, String... args) {
        Constructor<?> constructor = optionType.getDeclaredConstructors()[0];
        Parameter parameter = constructor.getParameters()[0];
        Optional option = parameter.getAnnotation(Optional.class);
        List<String> arguments = Arrays.asList(args);
        try {
            return (T) constructor.newInstance(arguments.contains(String.format("-%s", option.value())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
