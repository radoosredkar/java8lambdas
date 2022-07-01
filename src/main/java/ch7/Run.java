package ch7;

import ch1.SampleData;
import ch8.command.*;
import ch8.strategy.Compressor;
import ch8.strategy.GZipCompressionStrategy;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

public class Run {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Set<String> nationalities =
                SampleData.albums
                        .map(album -> album.getMainMusician())
                        //.filter(artist -> artist.getName().startsWith("The"))
                        .map(artist -> artist.getNationality())
                        .peek(nation -> System.out.println(nation))
                        .peek(nation -> {
                            nation = nation;
                        })
                        .collect(Collectors.toSet());
        System.out.println(nationalities);

        System.out.println("page 97");
    }
}
