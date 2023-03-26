package com.geektime.tdd.cli;

import java.util.List;

interface OptionParser {
    Object parse(List<String> arguments, Option option);
}
