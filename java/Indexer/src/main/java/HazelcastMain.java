import datalake.DataLake;

public class HazelcastMain {
    public static void main(String[] args) {
        HazelcastIndexer indexer = new HazelcastIndexer(new DataLake());
        indexer.indexAll();
    }
}
