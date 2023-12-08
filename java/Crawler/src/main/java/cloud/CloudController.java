package cloud;

import cloudDatalake.CloudDatalake;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CloudController {

    private CloudDatalake cloudDatalake;
    private CloudDownloader cloudDownloader;

    public CloudController(CloudDatalake cloudDatalake) {
        this.cloudDatalake = cloudDatalake;
        this.cloudDownloader = new CloudDownloader(cloudDatalake);
        this.start();
    }

    public void start() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        try {
            executor.scheduleAtFixedRate(() -> {
                cloudDownloader.run();
            }, 0, 1, TimeUnit.MINUTES);

            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public CloudDatalake getCloudDatalake() {
        return this.cloudDatalake;
    }
}
