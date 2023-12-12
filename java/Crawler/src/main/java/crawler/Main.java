package crawler;

import crawler.cloud.CloudController;
import cloud.CloudDatalake;
import fsystem.DataLake;
import crawler.filesys.Controller;

public class Main {
    public static void main(String[] args) {
        startCrawlerToCloud();
    }


    private static void startCrawlerToFileSystem(){
        DataLake dataLake = new DataLake();
        Controller crawler = new Controller(dataLake);
        crawler.start();
    }

    private static void startCrawlerToCloud(){
        CloudDatalake dataLake = new CloudDatalake();
        CloudController crawler = new CloudController(dataLake);
        crawler.start();
    }
}
