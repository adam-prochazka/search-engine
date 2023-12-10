package utils;

import utils.Book;

import java.util.List;

public interface BookReader {
    /**
     * Preprocess the text of the book and return it in a list of words.
     * @param text The text of the book.
     * @return A list of words after preprocessing.
     */
    List<String> preprocessText(String text);

    /**
     * Get the path separator based on the current operating system.
     * @return The path separator (e.g., "/" or "\").
     */
    String getPathSeparator();

    /**
     * Read a book from a given path and return a Book object containing the book name and its words.
     * @param path The path to the book file.
     * @return A Book object containing the book name and its words.
     */
    Book readBook(String path);
}
