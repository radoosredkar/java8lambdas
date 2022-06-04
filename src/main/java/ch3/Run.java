package ch3;

import ch1.Album;
import ch1.Artist;
import ch1.SampleData;
import ch1.Track;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class Run {
    static List<String> words = List.of("Rado,Vladka,Vlak,Avto,Stanovanje,Beseda".split(","));

    public static void main(String[] args) {
        var vCount =
                words.stream()
                        .filter(w -> w.startsWith("V"))
                        .count();
        System.out.println(vCount);

        words.stream()
                .filter(w -> {
                    System.out.println(w);
                    return w.startsWith("V");
                });
        words.stream()
                .filter(w -> {
                    System.out.println(w);
                    return w.startsWith("V");
                }).count();

        System.out.println(
                Stream.of("a", "b", "c", "d", "a", "d")
                        .collect(Collectors.toList()));

        System.out.println(
                Stream.of("a", "b", "c", "d", "a", "d")
                        .collect(Collectors.toSet()));

        System.out.println(
                Stream.of("a", "b", "c", "d", "a", "d")
                        .map(c -> c.toUpperCase())
                        .collect(Collectors.toList()));

        //Filter all streams
        Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(l -> l.stream().filter(n -> n % 2 == 0))
                .forEach(System.out::println);

        System.out.println(
                Stream.of("Rado", "Vladka", "Stanovanje", "Avto")
                        .min(Comparator.comparing(w -> -w.length())));

        Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .reduce((n1, n2) -> n1 + n2).ifPresent(n -> System.out.println(n));

        //Build test data
        var testAlbum = new Album(
                "Test Album",
                asList(
                        new Track("track1", 30),
                        new Track("track2", 10)
                ),
                asList(
                        new Artist("The band1", "It")
                        , new Artist("Lojze Slak ", "Sl")
                        , new Artist("The Siddhartha", "Sl")
                        , new Artist("The band 2 ", "USA")
                )
        );

        testAlbum.getMusicians()
                .filter(ar -> ar.getName().startsWith("The"))
                .map(ar -> ar.getNationality())
                .forEach(System.out::println);

        System.out.println("All tracks in all albums >1m");
        List<Album> albums = SampleData.albums.collect(Collectors.toList());
        findLongTracksOld(albums)
                .forEach(System.out::println);

        findLongTracks(albums)
                .forEach(System.out::println);

        Stream.of(1, 2, 3, 4, 5, 6)
                .reduce((add, n) -> add + n)
                .ifPresent(sum -> System.out.println(sum));

        var artists = SampleData.threeArtists().collect(Collectors.toList());
        artists.stream()
                .map(artist -> artist.getName() + ":" + artist.getNationality())
                .forEach(System.out::println);

        albums.stream()
                .filter(album -> album.getTracks().count() <= 3)
                .forEach(System.out::println);

        System.out.println(countMembersOld(artists));
        System.out.println(countMembers(artists));


        System.out.println("Zelo dolg string".chars()
                //.filter(c -> Character.isLowerCase((char)c))
                .filter(Character::isLowerCase)
                .count());

        Stream.of("Zelo dolg string", "netakodolg string", "HAHAHAHAHAHAHAHAHPRVISEM", "nenene")
                .max(Comparator.comparing(Run::countLowerCaseLetters))
                .ifPresent(s -> System.out.println(s));
    }

    public static Set<String> findLongTracksOld(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    public static long countLowerCaseLetters(String string){
       return string.chars()
               .filter(Character::isLowerCase)
               .count();
    }

    public static Set<String> findLongTracks(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.getTracks())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .collect(Collectors.toSet());
    }

    public static long countMembersOld(List<Artist> artists) {
        int totalMembers = 0;
        for (Artist artist : artists) {
            Stream<Artist> members = artist.getMembers();
            totalMembers += members.count();
        }
        return totalMembers;
    }

    public static long countMembers(List<Artist> artists) {
        return artists.stream()
                .flatMap(artist -> artist.getMembers())
                .count();
    }
}
