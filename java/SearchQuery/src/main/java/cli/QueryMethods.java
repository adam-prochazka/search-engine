package cli;

import cloud.CloudDatalake;
import impl.DataMart;
import impl.file.CloudDataMart;
import impl.file.FileDataMart;

import datalake.DataLake;

import java.util.List;

public class QueryMethods {

    private DataMart dataMart = new CloudDataMart();
    private CloudDatalake datalake = new CloudDatalake();

    public void queryWord(String word){
        System.out.println("The word " + word + " appears in:");
        List<Integer> invertedIndexOfWord = dataMart.getInvertedIndexOf(word);
        for(int i : invertedIndexOfWord){
            System.out.println("- "+datalake.getTitle(i)+"\n");
        }

    }
}
