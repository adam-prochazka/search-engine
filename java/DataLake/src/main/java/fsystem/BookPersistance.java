package fsystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class BookPersistance {

    private String path;

    public BookPersistance(String directory){
        this.path = directory;
    }


    public Map<Integer,String> load() {

        Map<Integer, String> map = null;
        String filePath = this.path + "books.json";
        File file = new File(filePath);

        if (file.exists()) {
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                map = objectMapper.readValue(file, new TypeReference<Map<Integer, String>>(){});

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
                map = new HashMap<>();
        }

        return map;
    }


    public void save(Map<Integer,String> books){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(books);

            String filePath = this.path + "/books.json";

            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(json);
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
