package com.geektime.tdd.cli;

class StringOptionParser extends IntOptionParser {
    public static OptionParser createStringOptionParser() {
        return new IntOptionParser(String::valueOf);
    }
}
