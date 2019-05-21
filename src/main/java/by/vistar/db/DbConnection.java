package by.vistar.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnection {

    public static Connection getConnection(){
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String password = resource.getString("password");
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            System.out.println("The driver isn't loaded");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            System.out.println("Error driver manager getConnection ");
            e.printStackTrace();
        }
        return null;
    }
}
