package com.geektime.tdd.cli;

class StringOptionParser extends IntOptionParser {
    @Override
    protected Object parseValue(String value) {
        return String.valueOf(value);
    }
}
