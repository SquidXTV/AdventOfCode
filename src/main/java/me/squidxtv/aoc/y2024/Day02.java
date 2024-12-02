package me.squidxtv.aoc.y2024;

import me.squidxtv.util.Day;

import java.util.Arrays;

public class Day02 extends Day {

    public Day02() {
        super(2, 2024);
    }

    @Override
    public long part1() {
        return input.stream()
                .map(line -> Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray())
                .filter(Day02::isSafe)
                .count();
    }

    @Override
    public long part2() {
        return input.stream()
                .map(line -> Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray())
                .filter(input -> isSafeWithToleration(input, false))
                .count();
    }

    private static boolean isSafe(int[] input) {
        int index = 0;
        if (index == input.length - 1) {
            return true;
        }

        int current = input[index];
        int next = input[index + 1];

        int distance = Math.abs(current - next);
        boolean isInRange = distance >= 1 && distance <= 3;

        return isInRange && isSafe(input, index + 1, current < next);
    }

    private static boolean isSafe(int[] input, int index, boolean increasing) {
        if (index == input.length - 1) {
            return true;
        }

        int current = input[index];
        int next = input[index + 1];

        if (increasing) {
            if (current >= next) {
                return false;
            }
        } else {
            if (current <= next) {
                return false;
            }
        }

        int distance = Math.abs(current - next);
        boolean isInRange = distance >= 1 && distance <= 3;

        return isInRange && isSafe(input, index + 1, current < next);
    }

    private static boolean isSafeWithToleration(int[] input, boolean toleratedAlready) {
        int index = 0;
        if (index == input.length - 1) {
            return true;
        }

        int current = input[index];
        int next = input[index + 1];

        int distance = Math.abs(current - next);
        boolean isInRange = distance >= 1 && distance <= 3;

        if (isInRange) {
            return isSafeWithToleration(input, index + 1, current < next, toleratedAlready);
        } else {
            if (toleratedAlready) {
                return false;
            }

            return isSafeWithToleration(copyWithoutIndex(input, index), true)
                    || isSafeWithToleration(copyWithoutIndex(input, index + 1), true);
        }
    }

    private static boolean isSafeWithToleration(int[] input, int index, boolean increasing, boolean toleratedAlready) {
        if (index == input.length - 1) {
            return true;
        }

        int current = input[index];
        int next = input[index + 1];

        if (current == next) {
            if (toleratedAlready) {
                return false;
            }

            if (index == 1) {
                return isSafeWithToleration(copyWithoutIndex(input, index), true);
            } else {
                return isSafeWithToleration(copyWithoutIndex(input, index), index - 1, input[index - 2] < input[index - 1], true);
            }

        }

        if ((increasing && current > next) || (!increasing && current < next)) {
            if (toleratedAlready) {
                return false;
            }

            if (index == 1) {
                return isSafeWithToleration(copyWithoutIndex(input, 0), true)
                        || isSafeWithToleration(copyWithoutIndex(input, index), true)
                        || isSafeWithToleration(copyWithoutIndex(input, index + 1), index, increasing, true);
            } else {
                return isSafeWithToleration(copyWithoutIndex(input, index), index - 1, input[index - 2] < input[index - 1], true)
                        || isSafeWithToleration(copyWithoutIndex(input, index + 1), index, increasing, true);
            }

        }

        int distance = Math.abs(current - next);
        boolean isInRange = distance >= 1 && distance <= 3;

        if (isInRange) {
            return isSafeWithToleration(input, index + 1, current < next, toleratedAlready);
        } else {
            if (toleratedAlready) {
                return false;
            }

            if (index == 1) {
                return isSafeWithToleration(copyWithoutIndex(input, index), true)
                        || isSafeWithToleration(copyWithoutIndex(input, index + 1), index, increasing, true);
            } else {
                return isSafeWithToleration(copyWithoutIndex(input, index), index - 1, input[index - 2] < input[index - 1], true)
                        || isSafeWithToleration(copyWithoutIndex(input, index + 1), index, increasing, true);
            }

        }
    }

    private static boolean isSafeOldVersion(int[] input) {
        boolean decreasing = true;
        for (int i = 0; i < input.length - 1; i++) {
            int current = input[i];
            int next = input[i + 1];

            if (i == 0 && (current < next)) {
                decreasing = false;
            }

            if (decreasing && (current < next)) {
                return false;
            }

            if (!decreasing && (current > next)) {
                return false;
            }

            int distance = Math.abs(current - next);
            if (distance < 1 || distance > 3) {
                return false;
            }
        }
        return true;
    }

    private static int[] copyWithoutIndex(int[] input, int index) {
        int[] result = new int[input.length - 1];
        System.arraycopy(input, 0, result, 0, index);
        System.arraycopy(input, index + 1, result, index, input.length - index - 1);
        return result;
    }

}
