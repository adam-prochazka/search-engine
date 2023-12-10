package impl.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import impl.DataMart;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("192.168.1.127");
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, List<Integer>> invertedIndexMap = hazelcastClient.getMap("invertedIndexMap");

        DataMart dataMart = new HazelcastDataMart(invertedIndexMap);

        String word1 = "world";

        System.out.println("Indexes of the word " + word1 + ": " + dataMart.getInvertedIndexOf(word1));

    }
}
