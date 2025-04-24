package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitFilterModel {
    private String name;
    private float[][] martix;
    private ArrayList<SplitFilterModel> filterList;

    public SplitFilterModel(String name, float[][] martix) {
        this.name = name;
        this.martix = martix;
    }
    public static List<SplitFilterModel> readFiltersFromFile(String filePath) throws IOException {
        List<SplitFilterModel> filterList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentName = null;
            float[][] currentMatrix = new float[3][3];
            int row = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Pomijaj puste linie
                if (line.isEmpty()) {
                    // Jeśli mamy kompletną macierz, dodaj do listy
                    if (currentName != null && row == 3) {
                        filterList.add(new SplitFilterModel(currentName, currentMatrix));
                        currentName = null;
                        row = 0;
                        currentMatrix = new float[3][3];
                    }
                    continue;
                }

                // Jeśli nie mamy nazwy, to jest to nowy filtr
                if (currentName == null) {
                    currentName = line;
                }
                // W przeciwnym razie wczytujemy wiersz macierzy
                else if (row < 3) {
                    String[] values = line.split("\\s+");
                    if (values.length != 3) {
                        throw new IOException("Nieprawidłowy format wiersza macierzy: " + line);
                    }

                    for (int col = 0; col < 3; col++) {
                        currentMatrix[row][col] = Float.parseFloat(values[col]);
                    }
                    row++;
                }
            }

            // Dodaj ostatni filtr, jeśli plik nie kończy się pustą linią
            if (currentName != null && row == 3) {
                filterList.add(new SplitFilterModel(currentName, currentMatrix));
            }

        } catch (NumberFormatException e) {
            throw new IOException("Nieprawidłowy format liczby w macierzy", e);
        }

        return filterList;
    }
    public String getName() {
        return name;
    }
    public float[][] getMartix() {
        return martix;
    }
}
