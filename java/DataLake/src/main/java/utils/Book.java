package utils;

import java.util.List;

public class Book {

    private static int count = 0;
    private int index;
    private String name;
    private List<String> words;


    public Book(String name, List<String> words) {
        this.name = name;
        this.words = words;
        this.index = count++;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex(){
        return this.index;
    }

    public List<String> getWords() {
        return this.words;
    }

}
