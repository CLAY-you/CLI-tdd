package com.geektime.tdd.cli;

import java.util.List;

class IntOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf(String.format("-%s", option.value()));
        return Integer.parseInt(arguments.get(index + 1));
    }
}
