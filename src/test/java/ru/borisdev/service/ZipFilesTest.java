package ru.borisdev.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import ru.borisdev.domain.androidstring.StringFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

class ZipFilesTest {
    @Test
    void zip() throws IOException {
        ZipFiles zipFiles = new ZipFiles(Arrays.asList(new StringFile("string-pl.xml", "asdfasdfasdfaa asdgasdfasdf"), new StringFile("string-de.xml", "123523452345 234523452 345234")));
        File file = new File("resources-string.zip");
        java.nio.file.Files.copy(zipFiles.zip(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(zipFiles.zip());
    }

}