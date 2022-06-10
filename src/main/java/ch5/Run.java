package ch5;

import ch1.Album;
import ch1.Artist;
import ch1.SampleData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class Run {

    public static void main(String[] args) {
        SampleData.threeArtists()
            .map(Artist::getName)
            .forEach(n -> System.out.println(n));
        Set<Integer> numbers = new HashSet<>(asList(4, 2, 1, 3));
        List<Integer> sameOrder = numbers.stream()
            .sorted()
            .collect(toList());
        assertEquals(asList(1, 2, 3, 4), sameOrder);

        //Album with the most members
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        var maxMembers = SampleData.threeArtists().collect(Collectors.maxBy(Comparator.comparing(getCount)));
        System.out.println(maxMembers);

        var albumsList = SampleData.albums.collect(toList());
        //Average no of tracks per album
        var average = albumsList.stream()
            .collect(Collectors.averagingInt(album -> album.getTrackList().size()));
        System.out.println(average);

        var artistList = SampleData.threeArtists().collect(toList());

        //Band or solo
        var twoLists =
            artistList.stream().collect(Collectors.partitioningBy(Artist::isSolo));

        System.out.println(twoLists.get(true));
        System.out.println(twoLists.get(false));

        var multipleListsChoosenKey =
            albumsList.stream().collect(Collectors.groupingBy(album -> album.getMainMusician()));

        System.out.println(multipleListsChoosenKey);

        //Build list of artists with bands

        StringBuilder stringBuilder = new StringBuilder();
        for (Artist artist : artistList) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(artist.getName());
        }
        System.out.println(stringBuilder);

        var collectedNames =
            artistList.stream()
                .map(artist -> artist.getName())
                .collect(Collectors.joining(", "));
        System.out.println(collectedNames);

        var countMusicians =
            albumsList.stream()
                .collect(Collectors.groupingBy(album -> album.getMainMusician(), Collectors.counting()));
        System.out.println(countMusicians);

        var albumsByArtist = albumsList.stream()
            .collect(groupingBy(Album::getMainMusician));

        Map<Artist, List<String>> allAlbumsOfArtist = new HashMap<>();
        for (var album : albumsByArtist.entrySet()) {
            allAlbumsOfArtist.put(
                album.getKey(),
                album.getValue().stream()
                    .map(Album::getName).collect(Collectors.toList())
            );

        }
        System.out.println(allAlbumsOfArtist);

        allAlbumsOfArtist =
            albumsList.stream()
                .collect(groupingBy(Album::getMainMusician, Collectors.mapping(Album::getName, toList())));

        System.out.println(allAlbumsOfArtist);

        //Refactor join (just how it can be done)
        StringBuilder builder = new StringBuilder();
        for (Artist artist : artistList) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            builder.append(artist.getName());
        }

        System.out.println(builder);
        /*artistList.stream()
            .reduce(null,null, null);*/

        String result =
            artistList.stream()
                .map(Artist::getName)
                .reduce(
                    new StringCombiner(", "),
                    StringCombiner::add,
                    StringCombiner::merge
                ).toString();
        System.out.println(result);

        //As collector
        artistList.stream()
            .map(Artist::getName)
            .collect(new StringCombinerStandard(", "));

        //Compute if absent
        Map<String, Artist> artistCache = new HashMap<>();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            names.add(RandomStringUtils.randomAlphabetic(10));
        }
        System.out.println("Loading cache");
        names.forEach((n) -> artistCache.computeIfAbsent(n, Run::readArtistFromDb));
        names.forEach((n) -> artistCache.computeIfAbsent(n, Run::readArtistFromDb));

        //Iteration over map
        Map<Artist, Integer> countOfAlbums = new HashMap<>();
        for (Album album : albumsList) {
            countOfAlbums.put(album.getMainMusician(), album.getTrackList().size());
        }
        System.out.println(countOfAlbums);

        Map<Artist, Integer> countOfAlbums1 = new HashMap<>();
        albumsList.forEach((album) -> countOfAlbums1.put(album.getMainMusician(), album.getTrackList().size()));
        System.out.println(countOfAlbums);

        var namesArray = Stream.of("John Lennon", "Paul McCartney",
            "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe").collect(toList());

        namesArray.stream()
                .map(String::toUpperCase)
                    .forEach(System.out::println);

        Stream<String> namesToCount = Stream.of("John", "Paul", "George", "John",
            "Paul", "John");

        System.out.println(namesToCount
            .collect(
                Collectors.groupingBy(n -> n, Collectors.counting())
            ));

        Run.fibonacci(10);
        System.out.println(cache);
        System.out.println("Page 76");
    }

    static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

    static {
        cache.put(0,0);
        cache.put(1,1);
    }
    private static int fibonacci(int x){
        return  cache.computeIfAbsent(x,n -> fibonacci(n-2) + fibonacci(n-1) );
    }

    private static Integer sumForFibonacci(Integer prevI, Integer i){
        return prevI + i;
    }

    private static Artist readArtistFromDb(String name) {
        System.out.println(String.format("Computing artist %s", name));
        return new Artist(name, "Si");
    }
}
