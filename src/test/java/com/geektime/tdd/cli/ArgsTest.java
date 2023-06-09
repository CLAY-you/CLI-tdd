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

    static record BooleanOption(@Option("l") boolean logging) {
    }

    @Test
    void should_parse_int_option_value() {
        IntOption option = Args.parse(IntOption.class, "-d", "8080");
        assertEquals(8080, option.port());
    }

    static record IntOption(@Option("d") int port) {
    }

    @Test
    void should_parse_string_option_value() {
        StrOption option = Args.parse(StrOption.class, "-d", "/user/logs");
        assertEquals("/user/logs", option.directory());
    }

    static record StrOption(@Option("d") String directory) {
    }

    //MultipleOption:
    // TODO: -l -p 8080 -d /usr/logs
    @Test
    void should_parse_multiple_options_at_same_time() {
        MultipleOptions options = Args.parse(MultipleOptions.class, "-l", "-p", "8080", "-d", "/user/logs");
        assertTrue(Objects.requireNonNull(options).logging());
        assertEquals(8080, options.port());
        assertEquals("/user/logs", options.directory());
    }

    static record MultipleOptions(@Option("l") boolean logging, @Option("p") int port,
                                  @Option("d") String directory) {
    }

    //sad path:
    // TODO: - Bool: -l t / -l t f
    // TODO: - Integer: -p / -p 8080 8081
    // TODO: - String: -d / -d /usr/logs /usr/vars

    //default value:
    // TODO: - Bool: false
    // TODO: - Integer: 0
    // TODO: - String: ""

    //-g this is a list -d 1 2 -3 5
    @Test
    @Disabled
    void should2_() {
        ListOptions options = Args.parse(ListOptions.class, "-g this is a list -d 1 2 -3 5");
        assertEquals(new String[]{"this", "is", "a", "list"}, Objects.requireNonNull(options).group());
        assertEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }
}
