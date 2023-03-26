package com.geektime.tdd.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionType, String... args) {
        try {
            Constructor<?> constructor = optionType.getDeclaredConstructors()[0];
            Parameter parameter = constructor.getParameters()[0];
            Optional option = parameter.getAnnotation(Optional.class);
            List<String> arguments = Arrays.asList(args);

            Object value = null;

            if (parameter.getType() == boolean.class) {
                value = arguments.contains(String.format("-%s", option.value()));
            }

            if (parameter.getType() == int.class) {
                int index = arguments.indexOf(String.format("-%s", option.value()));
                value = Integer.parseInt(arguments.get(index + 1));
            }

            if (parameter.getType() == String.class) {
                int index = arguments.indexOf(String.format("-%s", option.value()));
                value = arguments.get(index + 1);
            }

            return (T) constructor.newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
