package me.squidxtv.aoc.y2024;

import me.squidxtv.util.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Day04 extends Day {

    public Day04() {
        super(4, 2024);
    }

    @Override
    public long part1() {
        char[][] map = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        String target = "XMAS";
        char start = target.charAt(0);

        long count = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                char current = map[i][j];

                if (current != start) {
                    continue;
                }

                count += found(map, i, j, target);
            }
        }

        return count;
    }

    @Override
    public long part2() {
        char[][] map = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        List<Predicate<String>> matches = List.of(
                Pattern.compile("M.S\\n.A.\\nM.S", Pattern.MULTILINE).asMatchPredicate(),
                Pattern.compile("M.M\\n.A.\\nS.S", Pattern.MULTILINE).asMatchPredicate(),
                Pattern.compile("S.M\\n.A.\\nS.M", Pattern.MULTILINE).asMatchPredicate(),
                Pattern.compile("S.S\\n.A.\\nM.M", Pattern.MULTILINE).asMatchPredicate()
        );

        long count = 0;
        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {
                String box = "%c%c%c\n%c%c%c\n%c%c%c".formatted(
                        map[i - 1][j - 1], map[i - 1][j], map[i - 1][j + 1],
                        map[i][j - 1], map[i][j], map[i][j + 1],
                        map[i + 1][j - 1], map[i + 1][j], map[i + 1][j + 1]);

                for (Predicate<String> match : matches) {
                    if (match.test(box)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private static int found(char[][] map, int i, int j, String target) {
        boolean[] mismatch = new boolean[8];

        for (int k = 1; k <= target.length() - 1; k++) {
            char targetChar = target.charAt(k);

            // forward
            if (j + k >= map[i].length || map[i][j + k] != targetChar) {
                mismatch[0] = true;
            }

            // backwards
            if (j - k < 0 || map[i][j - k] != targetChar) {
                mismatch[1] = true;
            }

            // up
            if (i - k < 0 || map[i - k][j] != targetChar) {
                mismatch[2] = true;
            }

            // down
            if (i + k >= map.length || map[i + k][j] != targetChar) {
                mismatch[3] = true;
            }

            // top right
            if (i - k < 0 || j + k >= map[i - k].length || map[i - k][j + k] != targetChar) {
                mismatch[4] = true;
            }

            // top left
            if (i - k < 0 || j - k < 0 || map[i - k][j - k] != targetChar) {
                mismatch[5] = true;
            }

            // bottom right
            if (i + k >= map.length || j + k >= map[i + k].length ||  map[i + k][j + k] != targetChar) {
                mismatch[6] = true;
            }

            // bottom left
            if (i + k >= map.length || j - k < 0 || map[i + k][j - k] != targetChar) {
                mismatch[7] = true;
            }
        }

        int count = 0;
        for (boolean b : mismatch) {
            count += b ? 0 : 1;
        }

        return count;
    }

}
