package ru.borisdev.domain.androidstring;

public class StringFile {
    private final String name;
    private final String content;


    public StringFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
