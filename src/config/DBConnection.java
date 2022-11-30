package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "inventory_system_test";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public static Connection getConnection() {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URI = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
            conn = DriverManager.getConnection(URI, DB_USER, DB_PASS);
        } catch (Exception e) { throw new RuntimeException(); }
        return conn;
    }
}
