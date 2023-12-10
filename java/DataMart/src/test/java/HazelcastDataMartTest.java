import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import impl.DataMart;
import impl.hazelcast.HazelcastDataMart;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HazelcastDataMartTest {

    @Test
    void test() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("192.168.1.127");
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, List<Integer>> invertedIndexMap = hazelcastClient.getMap("invertedIndexMap");

        DataMart dataMart = new HazelcastDataMart(invertedIndexMap);

        String word1 = "trois";
        String word2 = "world";
        List<Integer> indexes1 = new ArrayList<>();
        List<Integer> indexes2 = new ArrayList<>();

        // Insert to empty
        int i0 = 4;
        indexes1.add(i0);
        dataMart.addBookIndexToWord(word1, i0);
        assertTrue(dataMart.getInvertedIndexOf(word1).containsAll(indexes1));
        System.out.println("Indexes of the word " + word1 + ": " + dataMart.getInvertedIndexOf(word1));

        // Multiple inserts to empty
        for (int i = 0; i < 5; i++) {
            indexes2.add(i);
            dataMart.addBookIndexToWord(word2, i);
        }
        assertTrue(dataMart.getInvertedIndexOf(word2).containsAll(indexes2));
        System.out.println("Indexes of the word " + word2 + ": " + dataMart.getInvertedIndexOf(word2));

        // Non-unique inserts
        for (int i = 0; i < 5; i++)
            dataMart.addBookIndexToWord(word2, i);
        assertTrue(dataMart.getInvertedIndexOf(word2).containsAll(indexes2));
        System.out.println("Indexes of the word " + word2 + ": " + dataMart.getInvertedIndexOf(word2));

        // Another inserts after non-unique
        indexes2.add(5);
        dataMart.addBookIndexToWord(word2, 5);
        assertTrue(dataMart.getInvertedIndexOf(word2).containsAll(indexes2));
        System.out.println("Indexes of the word " + word2 + ": " + dataMart.getInvertedIndexOf(word2));

        hazelcastClient.shutdown();
    }
}
