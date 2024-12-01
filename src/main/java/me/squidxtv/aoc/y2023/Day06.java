package me.squidxtv.aoc.y2023;

import me.squidxtv.util.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day06 extends Day {

    private static final int SPEED = 1; // 1 mm/ms

    public Day06() {
        super(6, 2023);
    }

    @Override
    public long part1() {
        long[] time = Arrays.stream(input.get(0).split(":")[1].trim().split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();
        long[] distance = Arrays.stream(input.get(1).split(":")[1].trim().split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();

        Map<Long, Long> mapping = new HashMap<>();
        for (int i = 0; i < time.length; i++) {
            mapping.put(time[i], distance[i]);
        }

        return mapping.entrySet().stream()
                .mapToLong(Day06::getNumberOfWins)
                .reduce((n1, n2) -> n1 * n2)
                .getAsLong();
    }

    @Override
    public long part2() {
        long[] time = Arrays.stream(input.get(0).split(":")[1].trim().split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();
        long[] distance = Arrays.stream(input.get(1).split(":")[1].trim().split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();

        long time2 = Long.parseLong(Arrays.stream(time)
                .mapToObj(String::valueOf)
                .reduce((s, s2) -> s + s2)
                .get());
        long distance2 = Long.parseLong(Arrays.stream(distance)
                .mapToObj(String::valueOf)
                .reduce((s, s2) -> s + s2)
                .get());
        return getNumberOfWins(Map.entry(time2, distance2));
    }

    private static long getNumberOfWins(Map.Entry<Long, Long> entry) {
        long time = entry.getKey();
        long distance = entry.getValue();
        long wins = 0;
        for (long i = 0; i <= time; i++) {
            long timeTraveling = time - i;
            long speed = i * SPEED;
            long dist = timeTraveling * speed;
            if (dist > distance) {
                wins++;
            }
        }
        return wins;
    }

}
