package fsystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class DataLake {
    private String path;
    private Map<Integer, String> bookNames;
    private BookPersistance persistance;

    public DataLake() {
        this.path = "dl/";
        this.createFolderIfNotExists(this.path);
        persistance = new BookPersistance(this.path);
        bookNames = persistance.load();
    }

    public void addBook(int bookIndex, String bookTitle) {
        bookNames.put(bookIndex, bookTitle);
        persistance.save(bookNames);
    }

    private void createFolderIfNotExists(String folderName) {
        File folder = new File(folderName);
        if (!folder.exists()) {
            boolean created = folder.mkdir();
        }
    }

    public boolean isBookInDataLake(String i) {
        File dir = new File(this.path);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.contains("(" + i + ")")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(int bookIndex) {
        return bookNames.get(bookIndex);
    }

    public String getDataLakePath(){
        return this.path;
    }

}
