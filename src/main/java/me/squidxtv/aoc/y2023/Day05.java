package me.squidxtv.aoc.y2023;

import me.squidxtv.util.Day;

import javax.crypto.spec.PSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day05 extends Day {

    private static final Predicate<String> NEW_CATEGORY = Pattern.compile(".+ map:").asMatchPredicate();

    public Day05() {
        super(5, 2023);
    }

    @Override
    public long part1() {
        List<Long> seeds = getSeeds(input.getFirst());
        List<List<Group>> groups = new ArrayList<>();
        for (String line : input.subList(1, input.size())) {
            if (line.isEmpty() || line.isBlank()) {
                continue;
            }
            if (NEW_CATEGORY.test(line)) {
                groups.add(new ArrayList<>());
                continue;
            }

            groups.getLast().add(new Group(line));
        }

        List<Long> locations1 = seeds.stream()
                .map(source -> mapToLast(groups, source))
                .toList();

        return locations1.stream().min(Long::compare).get();
    }

    @Override
    public long part2() {
        List<Long> seeds = getSeeds(input.getFirst());
        List<List<Group>> groups = new ArrayList<>();
        for (String line : input.subList(1, input.size())) {
            if (line.isEmpty() || line.isBlank()) {
                continue;
            }
            if (NEW_CATEGORY.test(line)) {
                groups.add(new ArrayList<>());
                continue;
            }

            groups.getLast().add(new Group(line));
        }

        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < seeds.size() - 1; i += 2) {
            pairs.add(new Pair(seeds.get(i), seeds.get(i + 1)));
        }

        return pairs.parallelStream()
                .flatMapToLong(pair -> LongStream.range(pair.start, pair.start + pair.range))
                .boxed()
                .map(seed -> mapToLast(groups, seed))
                .min(Long::compare)
                .orElse(0L);
    }

    private static List<Long> getSeeds(String line) {
        List<Long> seeds = new ArrayList<>();
        for (String seed : line.split(":")[1].trim().split("\\s+")) {
            seeds.add(Long.parseLong(seed));
        }
        return seeds;
    }

    private static long mapToLast(List<List<Group>> groups, long source) {
        long current = source;
        for (List<Group> group : groups) {
            current = mapToGroup(group, current);
        }
        return current;
    }

    private static long mapToGroup(List<Group> group, long source) {
        for (Group g : group) {
            if (source < g.source) {
                continue;
            }

            if (g.source + g.range - 1 < source) {
                continue;
            }

            return g.destination + (source - g.source);
        }
        return source;
    }

    // source -> destination
    private static class Group {

        private final long source;
        private final long destination;
        private final long range;

        public Group(String line) {
            String[] input = line.split(" ");
            source = Long.parseLong(input[1]);
            destination = Long.parseLong(input[0]);
            range = Long.parseLong(input[2]);
        }

    }

    private record Pair(long start, long range) {
    }

}
