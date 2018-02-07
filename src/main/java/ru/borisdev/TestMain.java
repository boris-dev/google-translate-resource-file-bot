package ru.borisdev;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) throws URISyntaxException {

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        // locate file system by using the syntax
        // defined in java.net.JarURLConnection
        Path path = Paths.get("res.zip");
        URI uri = URI.create("jar:" + path.toUri());
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("strings.xml");

        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path pathInZipfile = zipfs.getPath("strings.xml");

            // copy a file into the zip file
            Files.copy(resourceAsStream, pathInZipfile,
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
