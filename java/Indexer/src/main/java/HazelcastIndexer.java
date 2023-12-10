import com.hazelcast.map.IMap;
import datalake.Book;
import datalake.DataLake;
import datalake.Reader;
import impl.DataMart;
import impl.file.FileDataMart;
import impl.hazelcast.HazelcastDataMart;

import javax.sound.midi.Receiver;
import java.io.File;
import java.util.List;

public class HazelcastIndexer {

    private HazelcastDataMart hazelcastDataMart;
    private IMap<String, List<Integer>> invertedIndexMap;
    private DataLake dataLake;

    public HazelcastIndexer(DataLake dl) {
        hazelcastDataMart = new HazelcastDataMart(invertedIndexMap);
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
            hazelcastDataMart.addBookIndexToWord(word,index);
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


}

