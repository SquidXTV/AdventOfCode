package me.squidxtv.aoc.y2023;

import me.squidxtv.util.Algorithms;
import me.squidxtv.util.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day08 extends Day {

    public Day08() {
        super(8, 2023);
    }

    @Override
    public long part1() {
        String instructions = input.get(0);

        Map<String, List<String>> graph = input.subList(2, input.size()).stream()
                .map(s -> s.split("="))
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> mapDestinations(arr[1])
                ));

        return steps(graph, instructions);
    }

    @Override
    public long part2() {
        String instructions = input.get(0);

        Map<String, List<String>> graph = input.subList(2, input.size()).stream()
                .map(s -> s.split("="))
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> mapDestinations(arr[1])
                ));
        return steps2(graph, instructions);
    }

    private static int steps(Map<String, List<String>> graph, String instructions) {
        int steps = 0;
        boolean found = false;
        String current = "AAA";
        String goal = "ZZZ";
        while (!found) {
            for (char op : instructions.toCharArray()) {
                List<String> dest = graph.get(current);
                if(op == 'L') {
                    current = dest.get(0);
                } else if(op == 'R') {
                    current = dest.get(1);
                }
                steps++;

                if (current.equals(goal)) {
                    found = true;
                    break;
                }
            }
        }
        return steps;
    }

    private static long steps2(Map<String, List<String>> graph, String instructions) {
        List<String> starts = graph.keySet().stream()
                .filter(s -> s.endsWith("A"))
                .toList();

        char[] move = instructions.toCharArray();

        List<Integer> cycles = new ArrayList<>();
        for (String s : starts) {
            String current = s;
            int i = 0;
            while(true) {
                char op = move[i];
                List<String> dest = graph.get(current);
                current = (op == 'L') ? dest.getFirst() : dest.getLast();
                i++;
                if(i == move.length) {
                    i = 0;
                }
                if (current.endsWith("Z")) {
                    break;
                }
            }
            int cycle = 0;
            while(true) {
                char op = move[i];
                List<String> dest = graph.get(current);
                current = (op == 'L') ? dest.getFirst() : dest.getLast();
                cycle++;
                if (current.endsWith("Z")) {
                    break;
                }

                i++;
                if(i == move.length) {
                    i = 0;
                }
            }

            cycles.add(cycle);
        }

        return Algorithms.lcm(cycles.stream().mapToLong(value -> value).toArray());
    }

    private static List<String> mapDestinations(String line) {
        return Arrays.stream(line.trim()
                .split(","))
                .map(s -> s.replace("(", "").replace(")", "").trim())
                .toList();
    }

}
