package com.geektime.tdd.cli;

import java.util.List;
import java.util.function.Function;

class SingleValueOptionParser implements OptionParser {
    Function<String, Object> valueParser;

    public SingleValueOptionParser(Function<String, Object> valueParser) {
        this.valueParser = valueParser;
    }

    public static OptionParser createIntOptionParser() {
        return new SingleValueOptionParser(Integer::valueOf);
    }

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf(String.format("-%s", option.value()));
        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }
}
