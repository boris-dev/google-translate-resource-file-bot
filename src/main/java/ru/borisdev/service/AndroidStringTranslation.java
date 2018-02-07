package ru.borisdev.service;

import com.google.cloud.translate.Translate;
import ru.borisdev.domain.androidstring.StringFile;

public class AndroidStringTranslation {
    private final String content;
    private final String lang;
    private final Translate service;

    public AndroidStringTranslation(String content, String lang, Translate service) {
        this.content = content;
        this.lang = lang;
        this.service = service;
    }

    public StringFile traslateContentFromEnglishToLanguage() {
        String lines[] = content.split("<string");
        StringBuffer result = new StringBuffer();
        for (String line : lines) {
            if (line.contains("\">")) {
                String from = line.substring(line.indexOf("\">") + 2, line.indexOf("</"));
                String translatedText = service.translate(from, Translate.TranslateOption.sourceLanguage("en"), Translate.TranslateOption.targetLanguage(lang)).getTranslatedText();
                result.append("<string" + line.replace(from, translatedText));
            } else {
                result.append(line);
            }
        }

        return new StringFile("values-" + lang + ".xml", result.toString());
    }

}
