package models;

public class FilterModel {
    private String name;
    private int thershold;

    public FilterModel(String name, int thershold) {
        this.name = name;
        this.thershold = thershold;
    }

    public String getName() {
        return name;
    }

    public int getThershold() {
        return thershold;
    }
}
