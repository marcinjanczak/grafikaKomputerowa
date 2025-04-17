import models.FilterModel;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.ListIterator;

public class Test {
    public static void main(String[] args) throws IOException {
        var model = FilterModel.readFiltersFromFile("macierze.txt");
        for (FilterModel filterModel : model){
            System.out.println(filterModel.getName());
            float[][] matrix = filterModel.getMartix();
            for (float[] row : matrix){
                System.out.println(Arrays.toString(row));
            }
            System.out.println();
        }
    }
}
