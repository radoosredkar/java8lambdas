package ch8.strategy;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompressionStrategy implements CompressionStrategy {
    @Override
    public OutputStream compress(OutputStream data) throws IOException {
        ZipOutputStream gos = new ZipOutputStream(data);
        gos.putNextEntry(new ZipEntry("outZip"));
        return new ZipOutputStream(data);
    }
}
