package com.geektime.tdd.cli;

import java.util.List;
import java.util.function.Function;

class SingleValueOptionParser<T> implements OptionParser {
    Function<String, T> valueParser;

    public SingleValueOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf(String.format("-%s", option.value()));
        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }
}
