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
        return getOptionParser(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    private static OptionParser getOptionParser(Class<?> parameterType) {
        OptionParser parser = null;
        if (parameterType == boolean.class) {
            parser = new BooleanOptionParser();
        }

        if (parameterType == int.class) {
            parser = new IntOptionParser();
        }

        if (parameterType == String.class) {
            parser = new StringOptionParser();
        }
        return parser;
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
