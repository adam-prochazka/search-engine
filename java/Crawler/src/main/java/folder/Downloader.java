package folder;

import datalake.DataLake;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.util.Random;
import books.*;

public class Downloader {
    private DataLake dataLake;

    public Downloader(DataLake dl){
        dataLake = dl;
    }

    public void run() {
        try {
                Random random = new Random();
                int randomNumber = random.nextInt(70000) + 1;
                String numStr = String.valueOf(randomNumber);
                String bookUrl = "https://www.gutenberg.org/cache/epub/" + numStr + "/pg" + numStr + ".txt";

                if (dataLake.isBookInDataLake(numStr)) {
                    System.out.println("Book is already downloaded.");
                }else{
                    if (downloadBook(bookUrl, numStr)) {
                        System.out.println("Download completed.");
                    } else {
                        System.out.println("Book not found: " + bookUrl);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean downloadBook(String bookUrl, String i) {
        try {
            Connection.Response response = Jsoup.connect(bookUrl).ignoreContentType(true)
                    .execute();
            String book = response.parse().wholeText();

            if (book.length() > 0) {
                String title = ContentManager.getBookTitle(book);
                String finalTitle = ContentManager.cleanFilename(title);

                String fileName =  "(" + i + ")"+finalTitle+".txt";

                String bookContent = ContentManager.getBookContent(book);

                dataLake.saveToFile(dataLake.getDataLakePath() + fileName, bookContent);
                MessageSender.sendMessage(fileName);
                System.out.println("Book saved: " + fileName);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
