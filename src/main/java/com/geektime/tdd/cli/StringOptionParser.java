package com.geektime.tdd.cli;

import java.util.List;

class StringOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf(String.format("-%s", option.value()));
        return arguments.get(index + 1);
    }
}
