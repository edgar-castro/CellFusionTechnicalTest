package controller;

import config.DBConnection;
import model.Dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    private Connection conn;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public DashboardController() {
        conn = DBConnection.getConnection();
    }

    public List<Dashboard> list(){
        final String sql = "SELECT product.name AS p_name, product.total, product.sold, " +
                "warehouse_plant.name AS w_name, stock_status.color\n" +
                "FROM warehouse_plant_product \n" +
                "INNER JOIN warehouse_plant ON warehouse_plant.id = warehouse_plant_product.id_warehouse_plant\n" +
                "INNER JOIN product ON product.id = warehouse_plant_product.id_product\n" +
                "INNER JOIN stock_status ON warehouse_plant.stock_status = stock_status.id;";
        List<Dashboard> list = new ArrayList<Dashboard>();
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                String productName      = resultSet.getString(1);
                int total               = Integer.parseInt(resultSet.getString(2));
                int sold                = Integer.parseInt(resultSet.getString(3));
                String warehouseName    = resultSet.getString(4);
                String color            = resultSet.getString(5);
                list.add(new Dashboard(productName, total - sold, total, sold, warehouseName, color));
            }
        } catch (SQLException e){ throw new RuntimeException(); }
        return list;
    }
}
