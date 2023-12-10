package impl.file;

import impl.DataMart;
import persistence.GCloudBookIndexWordPersistence;
import persistence.repository.CloudRepository;
import util.inserter.list.UniqueIntegerListInserter;

import java.util.List;

public class CloudDataMart implements DataMart {
    private final UniqueIntegerListInserter uniqueListInserter = new UniqueIntegerListInserter();
    private final GCloudBookIndexWordPersistence bookIndexWordPersistence;

    public CloudDataMart() {
        CloudRepository cloudRepository = new CloudRepository();
        bookIndexWordPersistence = new GCloudBookIndexWordPersistence(cloudRepository);
    }

    @Override
    public List<Integer> getInvertedIndexOf(String word) {
        return bookIndexWordPersistence.getBookIndexesOf(word);
    }

    @Override
    public void addBookIndexToWord(String word, int bookIndex) {
        List<Integer> bookIndexes = getInvertedIndexOf(word);
        uniqueListInserter.insert(bookIndexes, bookIndex);
        bookIndexWordPersistence.saveBookIndexesOf(word, bookIndexes);
    }
}
