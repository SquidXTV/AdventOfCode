package me.squidxtv.aoc.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 extends Day {

    public Day09() {
        super(9, 2023);
    }

    @Override
    public long part1() {
        List<NumberHistory> history = input.stream()
                .map(NumberHistory::new)
                .toList();

        return history.stream()
                .mapToInt(NumberHistory::getExtrapolatedValue)
                .sum();
    }

    @Override
    public long part2() {
        List<NumberHistory> history = input.stream()
                .map(NumberHistory::new)
                .toList();
        return history.stream()
                .mapToInt(NumberHistory::getPreviousExtrapolatedValue)
                .sum();
    }

    private static class NumberHistory {

        private final int[] numbers;

        public NumberHistory(String line) {
            numbers = Arrays.stream(line.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        public int getExtrapolatedValue() {
            List<int[]> differences = getDifferences();
            int extra = 0;
            for (int[] diff : differences.reversed()) {
                extra = diff[diff.length - 1] + extra;
            }
            return extra;
        }

        public int getPreviousExtrapolatedValue() {
            List<int[]> differences = getDifferences();
            int extra = 0;
            for (int[] diff : differences.reversed()) {
                extra = diff[0] - extra;
            }
            return extra;
        }

        public List<int[]> getDifferences() {
            List<int[]> differences = new ArrayList<>();
            int[] current = numbers;
            while (!Arrays.stream(current).allMatch(value -> value == 0)) {
                differences.add(current);

                int[] difference = new int[current.length - 1];
                for (int i = 0; i < difference.length; i++) {
                    difference[i] = current[i + 1] - current[i];
                }
                current = difference;
            }
            return differences;
        }
    }

}
