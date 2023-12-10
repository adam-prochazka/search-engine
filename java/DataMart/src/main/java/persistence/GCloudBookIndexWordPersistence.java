package persistence;

import persistence.facade.CloudListPersistanceFacade;
import persistence.repository.CloudRepository;

public class GCloudBookIndexWordPersistence extends CloudListPersistanceFacade {
    public GCloudBookIndexWordPersistence(CloudRepository cloudRepository){
        super(cloudRepository);
    }
}
