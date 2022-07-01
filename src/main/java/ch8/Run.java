package ch8;

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

        /*try (
            GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream("/tmp/out.gz"));
            FileInputStream fis = new FileInputStream(Run.class.getResource("/toCompress").getFile());
        ){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gos.write(buffer, 0, len);
            }
        }

        try (
            ZipOutputStream gos = new ZipOutputStream(new FileOutputStream("/tmp/out.zip"));
            FileInputStream fis = new FileInputStream(Run.class.getResource("/toCompress").getFile());
        ){
            gos.putNextEntry(new ZipEntry("1"));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gos.write(buffer, 0, len);
            }
        }*/


        //Compress
        Compressor gzipCompressor = new Compressor(new GZipCompressionStrategy());
        gzipCompressor.compress(new File(Run.class.getResource("/toCompress").getFile()).toPath(), new File("/tmp/out.gz"));
        Compressor zipCompressor = new Compressor(new ZipCompressionStrategy());
        zipCompressor.compress(new File(Run.class.getResource("/toCompress").getFile()).toPath(), new File("/tmp/out.zip"));

    }
}
