package controller;

import config.DBConnection;
import model.Product;
import model.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseController {

    private Connection conn;
    PreparedStatement statement;
    ResultSet resultSet;

    public WarehouseController() {
        this.conn = DBConnection.getConnection();
    }

    public List<Warehouse> list() {
        List<Warehouse> warehouseList = new ArrayList<Warehouse>();
        final String sql = "SELECT * FROM warehouse_plant;";
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id      = Integer.parseInt(resultSet.getString(1));
                String name = resultSet.getString(2);
                int minProducts = Integer.parseInt(resultSet.getString(3));
                int maxProducts = Integer.parseInt(resultSet.getString(4));
                int stockStatus = Integer.parseInt(resultSet.getString(5));
                warehouseList.add(new Warehouse(id, name, minProducts, maxProducts, stockStatus));
            }
        } catch (SQLException e) { throw new RuntimeException(); }
        return warehouseList;
    }

    public Warehouse getById(int id) {
        final String query = "SELECT * FROM warehouse_plant WHERE id = ?;";
        Warehouse warehouse = new Warehouse();
        try{
            statement = this.conn.prepareStatement(query);
            statement.setString(1, String.valueOf(id));
            resultSet = this.statement.executeQuery();
            while(resultSet.next()){
                int id_warehouse = Integer.parseInt(resultSet.getString(1));
                String name      = resultSet.getString(2);
                int minProducts = Integer.parseInt(resultSet.getString(3));
                int maxProducts = Integer.parseInt(resultSet.getString(4));
                int stockStatus = Integer.parseInt(resultSet.getString(5));
                warehouse = new Warehouse(id, name, minProducts, maxProducts, stockStatus);
            }
        } catch (SQLException e) { throw new RuntimeException(); }
        return warehouse;
    }

    public void create(Warehouse warehouse) {
        final String sql = "INSERT INTO warehouse_plant (name, min_products, max_products, stock_status) VALUES (?, ?, ?, 1);";
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1, warehouse.getName());
            statement.setString(2, String.valueOf(warehouse.getMinProducts()));
            statement.setString(3, String.valueOf(warehouse.getMaxProducts()));
            statement.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(); }
    }

    public void update(Warehouse warehouse) {
        final String sql = "UPDATE warehouse_plant SET name = ?, min_products = ?, max_products = ? WHERE id = ?;";
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1, warehouse.getName());
            statement.setString(2, String.valueOf(warehouse.getMinProducts()));
            statement.setString(3, String.valueOf(warehouse.getMaxProducts()));
            statement.setString(4, String.valueOf(warehouse.getId()));
            statement.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); new RuntimeException(); }
    }

    public void delete(int id){
        final String sql = "DELETE FROM warehouse_plant WHERE id = ?;";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(); }
    }

    public void checkForProductAssign(int id_product, int id_warehouse){
        final String sql = "SELECT * FROM warehouse_plant_product WHERE id_warehouse_plant = ?;";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(id_warehouse));
            resultSet = statement.executeQuery();
            if(resultSet.next()) updateAssignProduct(id_product, id_warehouse);
            else assignProduct(id_product, id_warehouse);
        } catch (SQLException e) { throw new RuntimeException(); }
    }

    private void assignProduct(int id_product, int id_warehouse){
        final String sql = "INSERT INTO warehouse_plant_product (id_product, id_warehouse_plant) VALUES (?, ?);";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(id_product));
            statement.setString(2, String.valueOf(id_warehouse));
            statement.executeUpdate();
        } catch (SQLException e) {e.printStackTrace(); throw new RuntimeException(); }
    }

    private void updateAssignProduct(int id_product, int id_warehouse){
        final String sql = "UPDATE warehouse_plant_product SET id_product = ? WHERE id_warehouse_plant = ?;";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(id_product));
            statement.setString(2, String.valueOf(id_warehouse));
            statement.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(); }
    }

    public List<Product> availableProducts(){
        List<Product> list = new ArrayList<Product>();
        final String sql = "SELECT * FROM product WHERE product.id NOT IN (SELECT warehouse_plant_product.id_product from warehouse_plant_product);";
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id      = Integer.parseInt(resultSet.getString(1));
                String name = resultSet.getString(2);
                int total = Integer.parseInt(resultSet.getString(3));
                int sold = Integer.parseInt(resultSet.getString(4));
                list.add(new Product(id, name, total, sold));
            }
        } catch (SQLException e) { throw new RuntimeException(); }
        return list;
    }

    public Product getAssignedProduct(Warehouse warehouse){
        final String sql = "SELECT * FROM product WHERE id = (SELECT id_product FROM warehouse_plant_product WHERE id_warehouse_plant = ?);";
        Product product = new Product();
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(warehouse.getId()));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = Integer.valueOf(resultSet.getString(1));
                String name = resultSet.getString(2);
                int total = Integer.parseInt(resultSet.getString(3));
                int sold = Integer.parseInt(resultSet.getString(4));
                product = new Product(id, name, total, sold);
            }
        } catch (SQLException e) { throw new RuntimeException(); }
        return product;
    }
}
