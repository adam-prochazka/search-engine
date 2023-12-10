package folder;

import datalake.DataLake;
import folder.Downloader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    DataLake dataLake ;
    Downloader downloader;

    public Controller(DataLake dl){
        this.dataLake = dl;
        this.downloader = new Downloader(dataLake);
        this.start();
    }

    public void start(){

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        try {
            executor.scheduleAtFixedRate(() -> {
                downloader.run();
            }, 0, 1, TimeUnit.MINUTES);

            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }

    public DataLake getDataLake(){
        return this.dataLake;
    }



}
