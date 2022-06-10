package ch5;

import ch1.Album;
import ch1.Artist;
import ch1.SampleData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class Run {
    public static void main(String[] args) {
        SampleData.threeArtists()
                .map(Artist::getName)
                .forEach(n -> System.out.println(n));
        Set<Integer> numbers = new HashSet<>(asList(4, 2, 1, 3));
        List<Integer> sameOrder = numbers.stream()
                .sorted()
                .collect(Collectors.toList());
        assertEquals(asList(1, 2, 3, 4), sameOrder);

        //Album with the most members
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        var maxMembers = SampleData.threeArtists().collect(Collectors.maxBy(Comparator.comparing(getCount)));
        System.out.println(maxMembers);

        var albumsList = SampleData.albums.collect(Collectors.toList());
        //Average no of tracks per album
        var average = albumsList.stream()
                        .collect(Collectors.averagingInt(album -> album.getTrackList().size()));
        System.out.println(average);

        var artistList = SampleData.threeArtists().collect(Collectors.toList());

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
        for (Artist artist : artistList){
            if (stringBuilder.length()>1){
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


        System.out.println("Page 68");
    }
}
