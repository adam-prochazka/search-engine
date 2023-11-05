import javax.xml.crypto.Data;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    DataLake dataLake ;
    Downloader downloader;

    public Controller(){
        this.dataLake = new DataLake();
        this.downloader = new Downloader(dataLake);
        this.start();
    }

    public void start(){

        // Create a ScheduledExecutorService with a thread to execute scheduled tasks
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        try {
            // Execute a method every minute
            executor.scheduleAtFixedRate(() -> {
                // Call the method that will be executed every minute
                downloader.run();
            }, 0, 1, TimeUnit.MINUTES);// Start the task immediately and execute it every 1 minute

            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the executor once the program is ended.
            executor.shutdown();
        }

    }

    public DataLake getDataLake(){
        return this.dataLake;
    }



}
