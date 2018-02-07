package ru.borisdev.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import ru.borisdev.domain.androidstring.StringFile;

import java.io.IOException;
import java.io.InputStream;

class AndroidStringTranslationTest {

    @Test
    void traslateContentFromEnglishToLanguage() throws IOException {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("strings.xml");
        String s = IOUtils.toString(resourceAsStream);
        StringFile pl = new AndroidStringTranslation(s, "pl", null).traslateContentFromEnglishToLanguage();
        System.out.println(pl.getContent());
    }

}