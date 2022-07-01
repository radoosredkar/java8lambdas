package ch8.strategy;

import ch8.Run;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compressor {
    private final CompressionStrategy strategy;

    public Compressor(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void compress(Path inFile, File outFile) throws IOException {
        FileInputStream fis = new FileInputStream(inFile.toFile());
        try(OutputStream outputStream = new FileOutputStream(outFile)){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        }
    }
}
