package com.geektime.tdd.cli;

import java.util.List;

class IntOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf(String.format("-%s", option.value()));
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected Object parseValue(String value) {
        return Integer.parseInt(value);
    }
}
