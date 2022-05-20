package ru.job4j.urlshortcut.controller.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleGenerator {

    public static String generate() {
        List<Integer> nums = new ArrayList<>();
        IntStream.concat(
                IntStream.concat(
                        IntStream.range(48, 58), IntStream.range(65, 91)),
                IntStream.range(97, 123))
                .forEach(num -> nums.add(num));
        return new Random().ints(48, 123)
                .filter(i -> nums.contains(i))
                .mapToObj(i -> String.valueOf((char) i))
                .limit(10)
                .collect(Collectors.joining());
    }
}
