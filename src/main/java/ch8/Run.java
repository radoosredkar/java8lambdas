package ch8;

import ch8.command.Editor;
import ch8.command.EditorImpl;
import ch8.command.Macro;
import ch8.command.Open;
import ch8.command.Save;
import ch8.strategy.Compressor;
import ch8.strategy.GZipCompressionStrategy;
import java.io.File;
import java.io.IOException;

public class Run {

    public static void main(String[] args) throws IOException {
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

    }
}
