package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Good;
import by.vistar.entity.ListBaySell;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoListBaySell implements DaoImpl <Long, ListBaySell>{

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoListBaySell INSTANCE = null;

    private DaoListBaySell() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoListBaySell getInstance() throws SQLException {
        DaoListBaySell daoListBaySell = INSTANCE;
        if (daoListBaySell == null) {
            synchronized (DaoListBaySell.class) {
                daoListBaySell = INSTANCE;
                if (daoListBaySell == null) {
                    INSTANCE = daoListBaySell = new DaoListBaySell();
                }
            }
        }
        return daoListBaySell;
    }

    private void initPrepareStatement(Connection connection) throws SQLException {
        if (mysqlPrepareStatement == null) {
            mysqlPrepareStatement = new HashMap<>();
            mysqlPrepareStatement.put("addDoc", connection.prepareStatement(MYSQL_ADD_LIST, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getDoc", connection.prepareStatement(MYSQL_GET_LIST));
            mysqlPrepareStatement.put("editDoc", connection.prepareStatement(MYSQL_EDIT_LIST));
            mysqlPrepareStatement.put("dellDoc", connection.prepareStatement(MYSQL_DELL_LIST));
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Map<String, PreparedStatement> getMysqlPrepareStatement() {
        return mysqlPrepareStatement;
    }
    @Override
    public ListBaySell add(ListBaySell listBaySell) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addDoc");
        pst.setString(1, listBaySell.getNumber());
        pst.setDate(2, new Date(listBaySell.getDate().getTime()));
        pst.setLong(3, listBaySell.getGood().getId());
        pst.setFloat(4, listBaySell.getQuantity());
        pst.setLong(5, listBaySell.getSum());
        pst.setBoolean(6,listBaySell.getBay());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            listBaySell.setId(rs.getLong(1));
        }
        rs.close();
        return listBaySell;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellDoc");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public ListBaySell edit(ListBaySell listBaySell) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editDoc");
        pst.setString(1, listBaySell.getNumber());
        pst.setDate(2, new Date(listBaySell.getDate().getTime()));
        pst.setLong(3, listBaySell.getGood().getId());
        pst.setFloat(4, listBaySell.getQuantity());
        pst.setLong(5, listBaySell.getSum());
        pst.setBoolean(6,listBaySell.getBay());
        if (pst.executeUpdate() > 0) {
            return listBaySell;
        } else {
            return null;
        }
    }

    @Override
    public ListBaySell get(Long id) throws SQLException {
        ListBaySell listBaySell = null;
        Long goodId = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getDoc");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            listBaySell = new ListBaySell();
            listBaySell.setId(rs.getLong("id"));
            listBaySell.setNumber(rs.getString("number"));
            listBaySell.setDate(rs.getDate("date"));
            goodId = rs.getLong("good");
            listBaySell.setQuantity(rs.getFloat("quantity"));
            listBaySell.setSum(rs.getLong("sum"));
            listBaySell.setBay(rs.getBoolean("bay"));
        }
        if (goodId>0) {
            DaoGood daoGood = DaoGood.getInstance();
            listBaySell.setGood(daoGood.get(goodId));
        } else {
            listBaySell.setGood(null);
        }
        rs.close();
        return listBaySell;
    }
}
