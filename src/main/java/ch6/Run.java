package ch6;

import ch1.Album;
import ch1.SampleData;
import ch1.Track;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Run {

    public static void main(String[] args) {
        var albums = SampleData.albums.collect(Collectors.toList());

        var serialArraySum = albums.stream()
            .flatMap(Album::getTracks)
            .mapToInt(Track::getLength)
            .sum();
        System.out.println(String.format("Sum of tracks %s", serialArraySum));

        var parrallelArraySum = albums.parallelStream()
            .flatMap(Album::getTracks)
            .mapToInt(Track::getLength)
            .sum();
        System.out.println(String.format("Sum of tracks %s", parrallelArraySum));

        var result =
            IntStream.range(0, 1000)
                .parallel()
                .mapToObj(r -> Run.diceThrows(2))
                .collect(Collectors.groupingBy(s -> s, Collectors.summingDouble(n -> 1.0 / 6)));
        System.out.println(result);

        var arrInts = new ArrayList<Integer>();
        IntStream.range(0, 10000)
            .forEach(i -> arrInts.add(i));

        var linkedListInts = new LinkedList<Integer>();
        IntStream.range(0, 100000)
            .forEach(i -> linkedListInts.add(i));

        long start_time = System.currentTimeMillis();
        arrInts.stream()
            .parallel()
            .mapToInt(i -> i)
            .sum();
        System.out.println(System.currentTimeMillis() - start_time);

        start_time = System.currentTimeMillis();
        linkedListInts.stream()
            .parallel()
            .mapToInt(i -> i)
            .sum();
        System.out.println(System.currentTimeMillis() - start_time);

        //Set values to array
        double[] values = new double[10];

        Arrays.parallelSetAll(values, i -> i);
        Arrays.stream(values)
            .forEach(System.out::println);

        Arrays.parallelPrefix(values, Double::sum);
        Arrays.stream(values)
            .forEach(System.out::println);

        System.out.println(
            IntStream.range(0, 100)
                .parallel()
                .map(x -> x * x)
                .sum());

        List<Integer> listOfNumbers = IntStream.range(1, 3).boxed().collect(Collectors.toList());
        System.out.println(
            listOfNumbers.stream()
                .reduce(5, (acc, x) -> x * acc)
        );
        System.out.println( 5 *
            listOfNumbers.stream().parallel()
                .reduce(1, (acc, x) -> x * acc)
        );

        System.out.println("page 97");
    }

    private static int diceThrows(int noOfDices) {
        return
            IntStream.range(0, noOfDices)
                .map(d -> new Random().nextInt(6) + 1)
                .sum();
    }
}
