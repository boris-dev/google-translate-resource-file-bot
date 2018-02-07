package ru.borisdev.service;

import ru.borisdev.domain.androidstring.StringFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFiles {
    private final List<StringFile> stringFiles;

    public ZipFiles(List<StringFile> stringFiles) {
        this.stringFiles = stringFiles;
    }

    public InputStream zip() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (StringFile stringFile : stringFiles) {
                ZipEntry entry = new ZipEntry(stringFile.getName());
                zos.putNextEntry(entry);
                zos.write(stringFile.getContent().getBytes());
                zos.closeEntry();
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

}
