package me.squidxtv.aoc.y2024;

import me.squidxtv.util.Day;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 extends Day {

    public Day01() {
        super(1, 2024);
    }

    @Override
    public long part1() {
        return input.stream()
                .map(s -> s.split("\\s+"))
                .collect(Collectors.teeing(
                        Collectors.mapping(line -> Long.parseLong(line[0]), Collectors.toList()),
                        Collectors.mapping(line -> Long.parseLong(line[1]), Collectors.toList()),
                        (left, right) -> {
                            Collections.sort(left);
                            Collections.sort(right);

                            return IntStream.range(0, left.size()).mapToLong(i -> Math.abs(left.get(i) - right.get(i))).sum();
                        }));
    }

    @Override
    public long part2() {
        return input.stream()
                .map(s -> s.split("\\s+"))
                .collect(Collectors.teeing(
                        Collectors.mapping(line -> Integer.parseInt(line[0]), Collectors.toList()),
                        Collectors.mapping(line -> Integer.parseInt(line[1]), Collectors.toList()),
                        (left, right) -> {
                            Map<Integer, Long> counts = right.stream()
                                    .collect(Collectors.groupingBy(r -> r, Collectors.counting()));

                            return left.stream().mapToLong(l -> l * counts.getOrDefault(l, 0L)).sum();
                        }));
    }

}
