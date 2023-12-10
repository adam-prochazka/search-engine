import cloud.CloudController;
import cloud.CloudDatalake;
import datalake.DataLake;
import folder.Controller;

public class Main {
    public static void main(String[] args) {
        //startFileDataLake();
        startCrawlerToCloud();
    }


    private static void startCrawlerToFileSystem(){
        DataLake dataLake = new DataLake();
        Controller crawler = new Controller(dataLake);
        crawler.start();
    }

    private static void startCrawlerToCloud(){
        //CloudDatalake dataLake = new CloudDatalake();
        //CloudController crawler = new CloudController(dataLake);
        //crawler.start();
    }
}
