import java.util.List;

public interface DataMart {
    /**
     * Get the inverted list of book indexes for a given word.
     * @param word The word to get the inverted indexes for.
     * @return The inverted list of book indexes for the given word.
     */
    List<Integer> getInvertedIndexOfWord(String word);

    /**
     * Add an index of a book to a word.
     * @param word The word to add the book index to.
     * @param bookIndex The index of the book to add to the word.
     */
    void addBookIndexToWord(String word, int bookIndex);
}