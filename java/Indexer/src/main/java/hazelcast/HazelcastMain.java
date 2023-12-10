package hazelcast;

import datalake.DataLake;
import hazelcast.HazelcastIndexer;

public class HazelcastMain {
    public static void main(String[] args) {
        HazelcastIndexer indexer = new HazelcastIndexer(new DataLake());
        indexer.indexAll();
    }
}
