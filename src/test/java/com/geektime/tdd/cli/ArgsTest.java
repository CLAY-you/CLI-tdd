package com.geektime.tdd.cli;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgsTest {
    //-l -p 8080 -d /usr/logs

    //happy path:
    //SingleOption:
    @Test
    void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(Objects.requireNonNull(option).logging());
    }

    @Test
    void should_set_boolean_option_to_false_if_flag_is_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    static record BooleanOption(@Optional("l") boolean logging) {
    }

    // TODO: - Integer: -d

    @Test
    void should_parse_int_option_value() {
        IntOption option = Args.parse(IntOption.class, "-d", "8080");
        assertEquals(8080, option.port());
    }

    static record IntOption(@Optional("d") int port) {}
    // TODO: - String: -s
    //MultipleOption:
    // TODO: -l -p 8080 -d /usr/logs

    //sad path:
    // TODO: - Bool: -l t / -l t f
    // TODO: - Integer: -p / -p 8080 8081
    // TODO: - String: -d / -d /usr/logs /usr/vars

    //default value:
    // TODO: - Bool: false
    // TODO: - Integer: 0
    // TODO: - String: ""

    @Test
    @Disabled
    void should1_() {
        Options options = Args.parse(Options.class, "-l -p 8080 -d /usr/logs");
        assertTrue(Objects.requireNonNull(options).logging());
        assertEquals(8080, options.port());
        assertEquals("/user/logs", options.directory());
    }


    //-g this is a list -d 1 2 -3 5
    @Test
    @Disabled
    void should2_() {
        ListOptions options = Args.parse(ListOptions.class, "-g this is a list -d 1 2 -3 5");
        assertEquals(new String[]{"this", "is", "a", "list"}, Objects.requireNonNull(options).group());
        assertEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    static record Options(@Optional("l") boolean logging, @Optional("p") int port, @Optional("d") String directory) {
    }

    static record ListOptions(@Optional("g") String[] group, @Optional("d") int[] decimals) {
    }
}
