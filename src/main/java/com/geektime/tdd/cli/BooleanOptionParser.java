package com.geektime.tdd.cli;

import java.util.List;

class BooleanOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        return arguments.contains(String.format("-%s", option.value()));
    }
}
