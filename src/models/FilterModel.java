package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterModel {
    private String name;
    private float[][] martix;
//    private final List<FilterModel> filterModels;


    public FilterModel(String name, float[][] martix) {
        this.name = name;
        this.martix = martix;
    }


    private void readMatrixFromFile(String filePatch) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePatch))){
            String line;
            String currentname = null;
            List<String> matrixLines  = new ArrayList<>();

            while ((line = reader.readLine()) != null){
                line = line.trim();
                if(line.isEmpty()){

                }

            }
        }catch (FileNotFoundException e){
            System.err.println("Brak pliku do odczytania: "+e.getMessage());
        }
    }


}
