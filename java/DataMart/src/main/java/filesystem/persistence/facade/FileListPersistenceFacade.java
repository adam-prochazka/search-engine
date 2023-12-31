package filesystem.persistence.facade;

import filesystem.persistence.list.DataListPersistence;
import filesystem.persistence.list.ListPersistence;
import filesystem.persistence.repository.FileRepository;

import java.io.File;
import java.util.List;

public class FileListPersistenceFacade {
    private final FileRepository fileRepository;
    private static ListPersistence listPersistence = new DataListPersistence();

    public FileListPersistenceFacade(String path, String extension) {
        fileRepository = new FileRepository(path, extension);
    }

    public List<Integer> getBookIndexesOf(String word) {
        File wordFile = fileRepository.get(word);
        return listPersistence.read(wordFile);
    }

    public void saveBookIndexesOf(String word, List<Integer> bookIndexes) {
        File wordFile = fileRepository.get(word);
        listPersistence.save(wordFile, bookIndexes);
    }

}
