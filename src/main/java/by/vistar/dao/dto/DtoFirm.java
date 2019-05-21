package by.vistar.dao.dto;

import by.vistar.dao.entity.DaoFirm;
import by.vistar.db.DbConnection;
import by.vistar.entity.Firm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.MYSQL_GET_COUNT_ROW_FIRM;
import static by.vistar.db.DbConstants.MYSQL_GET_ID_TRUE_FIRM;

public class DtoFirm {
    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DtoFirm INSTANCE = null;

    private DtoFirm() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DtoFirm getInstance() throws SQLException {
        DtoFirm dtoFirm = INSTANCE;
        if (dtoFirm == null) {
            synchronized (DtoFirm.class) {
                dtoFirm = INSTANCE;
                if (dtoFirm == null) {
                    INSTANCE = dtoFirm = new DtoFirm();
                }
            }
        }
        return dtoFirm;
    }

    public Map<String, PreparedStatement> getMysqlPrepareStatement() {
        return mysqlPrepareStatement;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initPrepareStatement(Connection connection) throws SQLException {
        if (mysqlPrepareStatement == null) {
            mysqlPrepareStatement = new HashMap<>();
            mysqlPrepareStatement.put("getIdTrue", connection.prepareStatement(MYSQL_GET_ID_TRUE_FIRM));
            mysqlPrepareStatement.put("getCountRow", connection.prepareStatement(MYSQL_GET_COUNT_ROW_FIRM));
        }
    }

    public Long getMainId() throws SQLException {
        Long result = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getIdTrue");
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            result = (rs.getLong("id"));
        }
        rs.close();
        return result;
    }

    public Long getCountRow() throws SQLException{
        Long result = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getCountRow");
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            result = rs.getLong(1);
        }
        rs.close();
        return result;
    }

}
