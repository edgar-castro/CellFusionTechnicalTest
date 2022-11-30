package model;

public class StockStatus {

    private int id;
    private String meaning, color;

    public StockStatus(int id, String meaning, String color) {
        this.id = id;
        this.meaning = meaning;
        this.color = color;
    }

    public StockStatus() { this(-1, "", ""); }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
