import cloud.CloudDatalake;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import datalake.DataLake;
import impl.DataMart;
import impl.file.CloudDataMart;
import impl.file.FileDataMart;
import utils.Book;
import utils.CloudReader;
import utils.Reader;

import java.io.File;
import java.util.List;

public class CloudIndexer {

    private DataMart dataMart;
    private CloudDatalake dataLake = new CloudDatalake();

    public CloudIndexer() {
        dataMart = new CloudDataMart();
    }

    public void indexOne(Blob blob) {
        CloudReader reader = new CloudReader();
        Book book = reader.readBook(blob);
        int index = book.getIndex();
        String bookName = book.getName();
        List<String> words = book.getWords();
        System.out.println("[INDEXER]: Indexing book: \"" + bookName + "\"");
        for (String word : words) {
            dataMart.addBookIndexToWord(word,index);
        }
    }

    public void indexAll() {

        System.out.println("[INDEXER]: ------------------ Indexing starting -------------------");
        Storage storage = dataLake.getStorage();
        String bucketName = dataLake.getBucketName();
        Iterable<Blob> blobs = storage.list(bucketName).iterateAll();

        for (Blob blob : blobs) {
            if (blob.getSize() > 0) {
                indexOne(blob);
            }
        }
        System.out.println("[INDEXER]: -------------------- Indexing ended --------------------");
    }
}
