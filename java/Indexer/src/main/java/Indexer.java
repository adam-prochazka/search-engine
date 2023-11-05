import java.io.File;
import java.util.List;

public class Indexer {

    private DataMart dataMart;

    public Indexer() {
        dataMart = new FileDataMart();
    }

    public void indexOne(String path) {
        Reader reader = new Reader();
        Book book = reader.readBook(path);
        int index = book.getIndex();
        String bookName = book.getName();
        List<String> words = book.getWords();
        System.out.println("[INDEXER]: Indexing book: \"" + bookName + "\"");
        for (String word : words) {
            dataMart.addBookIndexToWord(word,index);
        }
    }

    public void indexAll(String directory) {
        System.out.println("[INDEXER]: ------------------ Indexing starting -------------------");
        File[] files = new File(directory).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    indexOne(files[i].getAbsolutePath());
                }
            }
        }
        System.out.println("[INDEXER]: -------------------- Indexing ended --------------------");
    }

}

