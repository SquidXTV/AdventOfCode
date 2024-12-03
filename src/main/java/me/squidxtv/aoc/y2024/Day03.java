package me.squidxtv.aoc.y2024;

import me.squidxtv.util.Day;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Day {

    public Day03() {
        super(3, 2024);
    }

    @Override
    public long part1() {
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        String all = input.stream().reduce(String::concat).orElse("");

        long sum = 0L;
        Matcher matcher = pattern.matcher(all);
        while (matcher.find()) {
            long first = Long.parseLong(matcher.group(1));
            long second = Long.parseLong(matcher.group(2));

            sum += first * second;
        }

        return sum;
    }

    @Override
    public long part2() {
        String test = "(?<!don't\\(\\))mul\\((\\d+),(\\d+)\\)";
        return 0;
    }

}
