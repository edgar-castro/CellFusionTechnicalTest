package controller;

import config.DBConnection;
import model.StockStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StockStatusController {

    private Connection conn;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public StockStatusController() {
        this.conn = DBConnection.getConnection();
    }

    public StockStatus getById(int id) {
        StockStatus stockStatus = new StockStatus();
        final String sql = "SELECT * FROM stock_status WHERE id = ?";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id_status = Integer.parseInt(resultSet.getString(1));
                String meaning = resultSet.getString(2);
                String color = resultSet.getString(3);
                stockStatus = new StockStatus(id_status, meaning, color);
            }
        } catch (SQLException e) { throw new RuntimeException(); }
        return  stockStatus;
    }



}
