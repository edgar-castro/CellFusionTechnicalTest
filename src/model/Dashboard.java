package model;

public class Dashboard {

    private String productName;
    private int onStock, sold, total;
    private String warehouseName, color;

    public Dashboard(String productName, int onStock, int sold, int total, String warehouseName, String color) {
        this.productName = productName;
        this.onStock = onStock;
        this.sold = sold;
        this.total = total;
        this.warehouseName = warehouseName;
        this.color = color;
    }

    public String getProductName() {
        return productName;
    }

    public int getOnStock() {
        return onStock;
    }

    public int getSold() {
        return sold;
    }

    public int getTotal() {
        return total;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getColor() {
        return color;
    }
}
