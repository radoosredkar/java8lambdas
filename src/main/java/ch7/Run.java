package ch7;

import ch1.SampleData;
import ch7.command.*;
import ch7.strategy.Compressor;
import ch7.strategy.GZipCompressionStrategy;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
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

        Macro macro = new Macro();
        Editor editor = new EditorImpl("Rado");
        Editor editor1 = new EditorImpl("Vladka");
        macro.record(new Open(editor));
        macro.record(new Save(editor));
        macro.record(new Open(editor1));
        macro.record(new Save(editor1));
        macro.run();

        //Or
        macro.record(() -> editor.open());
        macro.record(() -> editor.close());
        macro.record(() -> editor1.open());
        macro.record(() -> editor1.close());
        macro.run();

        //Or
        macro.record(editor::open);
        macro.record(editor::close);
        macro.record(editor1::open);
        macro.record(editor1::close);
        macro.run();


        //Compress
        Compressor gzipCompressor = new Compressor(new GZipCompressionStrategy());
        gzipCompressor.compress(new File("resources/toCompress").toPath(), new File("/tmp/outGzip"));
        Compressor zipCompressor = new Compressor(new GZipCompressionStrategy());

        System.out.println("page 97");
    }
}
