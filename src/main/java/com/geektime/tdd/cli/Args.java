package com.geektime.tdd.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionType, String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionType.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters()).map(param -> parseOption(arguments, param)).toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Optional option = parameter.getAnnotation(Optional.class);
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
        return value;
    }
}
