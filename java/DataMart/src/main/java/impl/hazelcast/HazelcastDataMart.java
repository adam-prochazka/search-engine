package impl.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import impl.DataMart;
import util.inserter.list.UniqueIntegerListInserter;

import java.util.ArrayList;
import java.util.List;

public class HazelcastDataMart implements DataMart {
    private final HazelcastInstance hazelcastInstance;
    private final IMap<String, List<Integer>> invertedIndexMap;
    private final UniqueIntegerListInserter uniqueListInserter = new UniqueIntegerListInserter();

    public HazelcastDataMart(IMap<String, List<Integer>> invertedIndexMap) {
        this.hazelcastInstance = Hazelcast.newHazelcastInstance(HazelcastConfig.getConfig());
        this.invertedIndexMap = hazelcastInstance.getMap("invertedIndexMap");
    }

    @Override
    public List<Integer> getInvertedIndexOf(String word) {
        return invertedIndexMap.get(word);
    }

    @Override
    public void addBookIndexToWord(String word, int bookIndex) {
        synchronized (invertedIndexMap) {
            List<Integer> bookIndexes = getInvertedIndexOf(word);
            if (bookIndexes == null) {
                bookIndexes = new ArrayList<>();
            }

            uniqueListInserter.insert(bookIndexes, bookIndex);
            invertedIndexMap.put(word, bookIndexes);
        }
    }
}
