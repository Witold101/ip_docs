package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Pay;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoPay implements DaoImpl<Long, Pay> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoPay INSTANCE = null;

    private DaoPay() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoPay getInstance() throws SQLException {
        DaoPay daoPay = INSTANCE;
        if (daoPay == null) {
            synchronized (DaoPay.class) {
                daoPay = INSTANCE;
                if (daoPay == null) {
                    INSTANCE = daoPay = new DaoPay();
                }
            }
        }
        return daoPay;
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
            mysqlPrepareStatement.put("addPay", connection.prepareStatement(MYSQL_ADD_PAY, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getPay", connection.prepareStatement(MYSQL_GET_PAY));
            mysqlPrepareStatement.put("editPay", connection.prepareStatement(MYSQL_EDIT_PAY));
            mysqlPrepareStatement.put("dellPay", connection.prepareStatement(MYSQL_DELL_PAY));
        }
    }


    @Override
    public Pay add(Pay pay) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addPay");
        pst.setTimestamp(1,Timestamp.valueOf(pay.getDate()));
        pst.setString(2, pay.getNumber());
        pst.setLong(3, pay.getCurrencyId());
        pst.setLong(4, pay.getPayerId());
        pst.setLong(5, pay.getPayerAccId());
        pst.setLong(6, pay.getSum());
        pst.setString(7, pay.getInfo());
        pst.setBoolean(8, pay.getDebetDoc());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            pay.setId(rs.getLong(1));
        }
        rs.close();
        return pay;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellPay");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public Pay edit(Pay pay) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editPay");
        pst.setTimestamp(1,Timestamp.valueOf(pay.getDate()));
        pst.setString(2, pay.getNumber());
        pst.setLong(3, pay.getCurrencyId());
        pst.setLong(4, pay.getPayerId());
        pst.setLong(5, pay.getPayerAccId());
        pst.setLong(6, pay.getSum());
        pst.setString(7, pay.getInfo());
        pst.setBoolean(8, pay.getDebetDoc());
        pst.setLong(9, pay.getId());
        if (pst.executeUpdate() > 0) {
            return pay;
        } else {
            return null;
        }
    }

    @Override
    public Pay get(Long id) throws SQLException {
        Pay pay = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getPay");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pay = new Pay();
            pay.setId(rs.getLong("id"));
            pay.setDate(rs.getTimestamp("date").toLocalDateTime());
            pay.setNumber(rs.getString("number"));
            pay.setCurrencyId(rs.getLong("curr_id"));
            pay.setPayerId(rs.getLong("payer_id"));
            pay.setPayerAccId(rs.getLong("payer_acc_id"));
            pay.setSum(rs.getLong("sum"));
            pay.setInfo(rs.getString("info"));
            pay.setDebetDoc(rs.getBoolean("debet_doc"));
        }
        rs.close();
        return pay;
    }
}
