package model;

public class Warehouse {

    private int id, minProducts, maxProducts, stockStatus;
    private String name;

    public Warehouse(int id, String name, int minProducts, int maxProducts, int stockStatus) {
        this.id = id;
        this.name = name;
        this.minProducts = minProducts;
        this.maxProducts = maxProducts;
        this.stockStatus = stockStatus;
    }

    public Warehouse(String name, int minProducts, int maxProducts, int stockStatus){ this(-1, name, minProducts, maxProducts, stockStatus); }

    public Warehouse() { this(-1, "", 0, 0, 0); }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinProducts() {
        return minProducts;
    }

    public void setMinProducts(int minProducts) {
        this.minProducts = minProducts;
    }

    public int getMaxProducts() {
        return maxProducts;
    }

    public void setMaxProducts(int maxProducts) {
        this.maxProducts = maxProducts;
    }

    public int getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(int stockStatus) {
        this.stockStatus = stockStatus;
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
