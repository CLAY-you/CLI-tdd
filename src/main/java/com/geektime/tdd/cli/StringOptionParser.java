package com.geektime.tdd.cli;

import java.util.function.Function;

class StringOptionParser extends IntOptionParser {
    public StringOptionParser() {
        super(String::valueOf);
    }
}
