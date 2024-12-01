package me.squidxtv.aoc.y2023;

import me.squidxtv.util.Day;

import java.util.Optional;

public class Day01 extends Day {

    public Day01() {
        super(1, 2023);
    }

    @Override
    public long part1() {
        return input.stream().mapToInt(Day01::toNumber).sum();
    }

    @Override
    public long part2() {
        return input.stream()
                .mapToInt(line -> {
                    char first = 0;
                    char last = 0;
                    char[] chars = line.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        char current = chars[i];
                        if (Character.isDigit(current)) {
                            if (first == 0) {
                                first = current;
                            }
                            last = current;
                        } else {
                            Optional<Number> number = startsWithNumber(line.substring(i));
                            if (number.isEmpty()) {
                                continue;
                            }
                            last = number.get().character;
                            if (first == 0) {
                                first = last;
                            }
                        }

                    }
                    return Integer.parseInt("" + first + last);
                }).sum();
    }

    private static int toNumber(String line) {
        String s = line.replaceAll("\\D", "");
        return Integer.parseInt(s.charAt(0) + "") * 10 + Integer.parseInt(s.charAt(s.length() - 1) + "");
    }

    enum Number {

        ZERO('0'),
        ONE('1'),
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9');

        private final String name;
        private final char character;

        Number(char character) {
            this.name = name().toLowerCase();
            this.character = character;
        }

    }

    private static Optional<Number> startsWithNumber(String text) {
        for (Number number : Number.values()) {
            if (text.startsWith(number.name)) {
                return Optional.of(number);
            }
        }
        return Optional.empty();
    }

}
