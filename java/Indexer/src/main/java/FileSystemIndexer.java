import utils.Book;
import datalake.DataLake;
import utils.Reader;
import impl.DataMart;
import impl.file.FileDataMart;

import java.io.File;
import java.util.List;

public class FileSystemIndexer {

    private DataMart dataMart;
    private DataLake dataLake;

    public FileSystemIndexer(DataLake dl) {
        dataMart = new FileDataMart();
        dataLake = dl;
    }

    public void indexOne(String path) {
        Reader reader = new Reader(dataLake);
        Book book = reader.readBook(path);
        int index = book.getIndex();
        String bookName = book.getName();
        List<String> words = book.getWords();
        System.out.println("[INDEXER]: Indexing book: \"" + bookName + "\"");
        for (String word : words) {
            dataMart.addBookIndexToWord(word,index);
        }
    }

    public void indexAll() {
        String directory = dataLake.getDataLakePath();
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

    public void indexQueue(){
        new Receiver();
    }

}

