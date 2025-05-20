import models.SplitFilterModel;

import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        var model = SplitFilterModel.readFiltersFromFile("macierze.txt");
        for (SplitFilterModel filterModel : model) {
            System.out.println(filterModel.getName());
            float[][] matrix = filterModel.getMartix();
            for (float[] row : matrix) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println();
        }
    }
}
