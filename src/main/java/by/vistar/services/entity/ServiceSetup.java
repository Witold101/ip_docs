package by.vistar.services.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public class ServiceSetup {

    Connection connection;

    public ServiceSetup() {
    }

    public void setConnection(Connection connection){
           if (connection!=null) {
            this.connection = connection;
        }else {
            try {
                throw new SQLException();
            } catch (SQLException e) {
                System.out.println("Connection = NULL ");
                e.printStackTrace();
            }
        }
    }

    public void startTransaction() {
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Error init setAutocommit = false");
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            System.out.println("Error commit");
            e.printStackTrace();
        }
    }

    public void closeAll(Connection connection, Collection<PreparedStatement> setPrs){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error CLOSE CONNECTION.");
            e.printStackTrace();
        }
        for (PreparedStatement prs : setPrs) {
            try {
                prs.close();
            } catch (SQLException e) {
                System.out.println("Error CLOSE PREPARESTATEMENT.");
                e.printStackTrace();
            }
        }
    }
}
