package by.vistar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static by.vistar.db.DbInitTables.*;

public class DbInit {

    private static Boolean firstStart = true;

    public static Boolean getFirstStart() {
        return firstStart;
    }

    public static void setFirstStart(Boolean firstStart) {
        DbInit.firstStart = firstStart;
    }

    public static void initTable() throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement pst = connection.prepareStatement(MYSQL_INIT_PARTYS_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_GOODS_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_LIST_BAY_SEL_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_FIRMS_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_USERS_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_CURR_CODE_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_BANKS_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_ACC_TABLE);
        pst.execute();
        pst = connection.prepareStatement(MYSQL_INIT_BANK_DOC_TABLE);
        pst.execute();
        pst.close();
        connection.close();
        firstStart = false;
    }
}
