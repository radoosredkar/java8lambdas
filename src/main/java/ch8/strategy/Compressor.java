package ch8.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compressor {
    private final CompressionStrategy strategy;

    public Compressor(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void compress(Path inFile, File outFile) throws IOException {
        try(OutputStream outputStream = new FileOutputStream(outFile)){
            Files.copy(inFile, strategy.compress(outputStream));
        }
    }
}
