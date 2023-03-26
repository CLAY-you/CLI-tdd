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

    interface OptionParser {
        Object parse(List<String> arguments, Option option);
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Option option = parameter.getAnnotation(Option.class);
        Object value = null;

        if (parameter.getType() == boolean.class) {
            value = parseBoolean(arguments, option);
        }

        if (parameter.getType() == int.class) {
            value = parseInt(arguments, option);
        }

        if (parameter.getType() == String.class) {
            value = parseString(arguments, option);
        }
        return value;
    }

    private static Object parseString(List<String> arguments, Option option) {
        return new StringOptionParser().parse(arguments, option);
    }

    private static Object parseInt(List<String> arguments, Option option) {
        return new IntOptionParser().parse(arguments, option);
    }

    private static Object parseBoolean(List<String> arguments, Option option) {
        return new BooleanOptionParser().parse(arguments, option);
    }

    static class BooleanOptionParser implements OptionParser {
        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.contains(String.format("-%s", option.value()));
        }
    }

    static class IntOptionParser implements OptionParser {
        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf(String.format("-%s", option.value()));
            return Integer.parseInt(arguments.get(index + 1));
        }
    }

    static class StringOptionParser implements OptionParser {
        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf(String.format("-%s", option.value()));
            return arguments.get(index + 1);
        }
    }
}
