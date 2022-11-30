package controller;

import config.DBConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private Connection conn;
    PreparedStatement statement;
    ResultSet resultSet;

    public ProductController() {
        this.conn = DBConnection.getConnection();
    }

    public List<Product> list() {
        List<Product> list = new ArrayList<Product>();
        final String sql = "SELECT * FROM product";
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

    public Product getById(int id) {
        final String query = "SELECT * FROM product WHERE id = ?";
        Product product = new Product();
        try{
            statement = conn.prepareStatement(query);
            statement.setString(1, String.valueOf(id));
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id_product = Integer.parseInt(resultSet.getString(1));
                String name = resultSet.getString(2);
                int total = Integer.parseInt(resultSet.getString(3));
                int sold = Integer.parseInt(resultSet.getString(4));
                product = new Product(id_product, name, total, sold);
            }
        } catch (SQLException e) { throw new RuntimeException(); }
        return product;
    }

    public void create(Product product) {
        final String sql = "INSERT INTO product (name, total, sold) VALUES (?, ?, ?);";
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, String.valueOf(product.getTotal()));
            statement.setString(3, String.valueOf(product.getSold()));
            statement.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(); }
    }

    public void update(Product product) {
        final String sql = "UPDATE product SET name = ?, total = ?, sold = ? WHERE id = ?;";
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, String.valueOf(product.getTotal()));
            statement.setString(3, String.valueOf(product.getSold()));
            statement.setString(4, String.valueOf(product.getId()));
            System.out.println(statement);
            statement.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(); }
    }

    public void delete(int id){
//        final String sql = "DELETE FROM product WHERE id = ?; DELETE FROM warehouse_plant_product WHERE id_product = ?";
        final String sql = "DELETE FROM product WHERE id = ?;";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); throw new RuntimeException(); }
    }

}
