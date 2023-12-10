package impl.hazelcast;

import com.hazelcast.config.Config;

public class HazelcastConfig {
    public static Config getConfig() {
        Config config = new Config();
        config.getNetworkConfig().setPublicAddress("192.168.1.127");
        return config;
    }
}
