package persistence.facade;

import persistence.repository.CloudRepository;
import persistence.list.CloudListPersistance;

import java.util.List;

public class CloudListPersistanceFacade {
    private final CloudRepository cloudRepository;
    private static CloudListPersistance listPersistence = new CloudListPersistance();

    public CloudListPersistanceFacade(CloudRepository cloudRepository) {
        this.cloudRepository = cloudRepository;
    }

    public List<Integer> getBookIndexesOf(String word) {
        return listPersistence.read(word);
    }

    public void saveBookIndexesOf(String word, List<Integer> bookIndexes) {
        listPersistence.save(word, bookIndexes);
    }
}
