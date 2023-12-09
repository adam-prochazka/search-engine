
import cloud.*;
import datalake.DataLake;
import folder.Controller;

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
        CloudDatalake cloudDatalake = new CloudDatalake();
        CloudController cloudController = new CloudController(cloudDatalake);
        cloudController.start();
    }
}
