package model;

public class Product {

    private int id, total, sold;
    private String name;

    public Product(int id, String name, int total, int sold) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.sold = sold;
    }

    public Product(String name, int total, int sold){ this(-1, name, total, sold); }

    public Product() { this(-1, "", 0, 0); }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
