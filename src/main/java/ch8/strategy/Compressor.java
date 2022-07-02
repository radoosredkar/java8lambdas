package ch8.strategy;

import ch8.Run;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;

public class Compressor {
    private final CompressionStrategy strategy;

    public Compressor(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void compress(Path inFile, File outFile) throws IOException {
        try(
            OutputStream outputStream = this.strategy.compress(new FileOutputStream(outFile));
            FileInputStream fis = new FileInputStream(inFile.toFile());
        ){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        }
    }
}
