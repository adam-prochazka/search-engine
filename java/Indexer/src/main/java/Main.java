import datalake.DataLake;

public class Main {
    public static void main(String[] args) {
        indexCloud();
    }


    private static void indexFileSystem(){
        FileSystemIndexer fileSystemIndexer = new FileSystemIndexer(new DataLake());
        fileSystemIndexer.indexAll();
    }

    private static void indexCloud(){
        CloudIndexer indexer = new CloudIndexer();
        indexer.indexAll();
    }

}
