import datalake.DataLake;
import folder.Controller;

public class Main {
    public static void main(String[] args) {
        DataLake dataLake = new DataLake();
        Controller crawler = new Controller(dataLake);
        crawler.start();
    }
}
