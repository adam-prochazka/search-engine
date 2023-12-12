package crawler.cloud;

import cloud.CloudDatalake;
import crawler.ctrl.ContentManager;
import crawler.ctrl.MessageSender;
import crawler.filesys.Downloader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class CloudDownloader {
    private CloudDatalake cloudDatalake;
    public static Logger logger = LoggerFactory.getLogger(Downloader.class);

    public CloudDownloader(CloudDatalake cloudDatalake) {
        this.cloudDatalake = cloudDatalake;
    }

    public void run() {
        try {
            Random random = new Random();
            int randomNumber = random.nextInt(70000) + 1;
            String numStr = String.valueOf(randomNumber);
            String bookUrl = "https://www.gutenberg.org/cache/epub/" + numStr + "/pg" + numStr + ".txt";

            if (cloudDatalake.isBookInDataLake(numStr)) {
                logger.info("Book is already downloaded.");
            } else {
                if (downloadBook(bookUrl, numStr)) {
                    logger.info("Download completed.");
                } else {
                    logger.error("Book not found: " + bookUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean downloadBook(String bookUrl, String i) {
        try {
            logger.info("Trying connection...");
            Connection.Response response = Jsoup.connect(bookUrl).timeout(30000).ignoreContentType(true).execute();
            String book = response.parse().wholeText();

            if (book.length() > 0) {
                String title = ContentManager.getBookTitle(book);
                String finalTitle = ContentManager.cleanFilename(title);

                String fileName = "(" + i + ")" + finalTitle + ".txt";

                String bookContent = ContentManager.getBookContent(book);

                cloudDatalake.saveToCloud(fileName, bookContent);
                MessageSender.sendMessage(fileName);
                logger.info("Book saved to Google Cloud Storage: " + fileName);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            logger.error("Connection failed. Retrying...");
            e.printStackTrace();
            return false;
        }
    }
}
