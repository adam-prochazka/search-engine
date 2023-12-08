package cloudDatalake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;

public class BookPersistenceCloud {
    private String bucketName;
    private String objectName;

    public BookPersistenceCloud(String bucketName, String objectName) {
        this.bucketName = bucketName;
        this.objectName = objectName;
    }

    public Map<Integer, String> load() {
        try {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            Blob blob = storage.get(bucketName, objectName);
            if (blob != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(new InputStreamReader(Channels.newInputStream(blob.reader())), new TypeReference<Map<Integer, String>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void save(Map<Integer, String> books) {
        try {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            Blob blob = storage.get(bucketName, objectName);
            OutputStream outputStream = Channels.newOutputStream(blob.writer());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, books);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsBook(String i) {
        try {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            Blob blob = storage.get(bucketName, objectName);
            if (blob != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<Integer, String> bookMap = objectMapper.readValue(new InputStreamReader(Channels.newInputStream(blob.reader())), new TypeReference<Map<Integer, String>>() {});
                for (String title : bookMap.values()) {
                    if (title.contains("(" + i + ")")) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
