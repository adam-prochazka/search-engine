package cloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;

public class BookPersistenceCloud {
    private String bucketName;
    private Storage storage;

    public BookPersistenceCloud(String bucketName, Storage st) {
        this.bucketName = bucketName;
        this.storage = st;
    }

    public Map<Integer, String> load() {
        try {
            Blob blob = storage.get(bucketName, "books.json");
            if (blob != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(new InputStreamReader(Channels.newInputStream(blob.reader())), new TypeReference<Map<Integer, String>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void save(Map<Integer,String> books){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(books);

            Blob blob = storage.get(bucketName, "books.json");

            if (blob == null) {
                blob = storage.create(Blob.newBuilder(bucketName, "books.json").build());
            }

            OutputStream outputStream = Channels.newOutputStream(blob.writer());
            outputStream.write(json.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
