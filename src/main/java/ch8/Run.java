package ch8;

import ch7.observer.Aliens;
import ch7.observer.LandingObserver;
import ch7.observer.Moon;
import ch7.observer.Nasa;
import ch8.command.Editor;
import ch8.command.EditorImpl;
import ch8.command.Macro;
import ch8.command.Open;
import ch8.command.Save;
import ch8.strategy.Compressor;
import ch8.strategy.GZipCompressionStrategy;
import ch8.strategy.ZipCompressionStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Run {

    public static void main(String[] args) throws IOException {
        //Command pattern
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

        //Strategy pattern
        //Compressor gzipCompressor = new Compressor(new GZipCompressionStrategy());
        Compressor gzipCompressor = new Compressor(GZIPOutputStream::new);
        gzipCompressor.compress(new File(Run.class.getResource("/toCompress").getFile()).toPath(), new File("/tmp/out.gz"));
        Compressor zipCompressor = new Compressor(new ZipCompressionStrategy());
        zipCompressor.compress(new File(Run.class.getResource("/toCompress").getFile()).toPath(), new File("/tmp/out.zip"));

        //Observer pattern
        Moon moon = new Moon();
        moon.startObserving(new Aliens());
        moon.startObserving(new Nasa());
        moon.land("an Asteroid");
        moon.land("Apollo 11");

        //Or
        moon = new Moon();
        moon.startObserving((name) -> {
            if (name.contains("Asteroid")){
                System.out.println("Good job landing on " + name);
            }
        });
        moon.startObserving((name) -> {
            if (name.contains("Apollo")){
                System.out.println("They are distracted, let's invade Earth!");
            }
        });
        moon.land("an Asteroid");
        moon.land("Apollo 11");
    }
}
