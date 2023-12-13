package indexer;

import datalake.filesystem.DataLake;
import datalake.utils.Book;
import datalake.utils.Reader;
import impl.DataMart;
import impl.file.FileSystemDataMart;

import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Indexer {
    private final DataMart dataMart;
    private final DataLake dataLake;
    private static final Logger logger = LoggerFactory.getLogger(Indexer.class);

    public Indexer(DataLake dl) {
        dataMart = new FileSystemDataMart();
        dataLake = dl;
    }

    public void indexOne(String path) {
        Reader reader = new Reader(dataLake);
        Book book = reader.readBook(path);
        int index = book.getIndex();
        String bookName = book.getName();
        List<String> words = book.getWords();
        logger.info("Indexing: \"" + bookName + "\"");
        for (String word : words) {
            dataMart.addBookIndexToWord(word,index);
        }
        logger.info("Done: \"" + bookName + "\"");
    }

    public void indexAll() {
        String directory = dataLake.getDataLakePath();
        System.out.println("[INDEXER]: ------------------ Indexing starting -------------------");
        File[] files = new File(directory).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    indexOne(file.getAbsolutePath());
                }
            }
        }
        System.out.println("[INDEXER]: -------------------- Indexing ended --------------------");
    }

    public void indexQueue(){
        Receiver receiver = new Receiver(dataLake);
        receiver.start();
    }

}

