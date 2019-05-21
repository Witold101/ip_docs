package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Account;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoAccount implements DaoImpl<Long, Account> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoAccount INSTANCE = null;

    private DaoAccount() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoAccount getInstance() throws SQLException {
        DaoAccount daoAccount = INSTANCE;
        if (daoAccount == null) {
            synchronized (DaoAccount.class) {
                daoAccount = INSTANCE;
                if (daoAccount == null) {
                    INSTANCE = daoAccount = new DaoAccount();
                }
            }
        }
        return daoAccount;
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
            mysqlPrepareStatement.put("addAcc", connection.prepareStatement(MYSQL_ADD_ACC, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getAcc", connection.prepareStatement(MYSQL_GET_ACC));
            mysqlPrepareStatement.put("editAcc", connection.prepareStatement(MYSQL_EDIT_ACC));
            mysqlPrepareStatement.put("dellAcc", connection.prepareStatement(MYSQL_DELL_ACC));
        }
    }

    @Override
    public Account add(Account account) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addAcc");
        pst.setString(1,account.getNumber());
        pst.setLong(2, account.getCurrencyId());
        pst.setLong(3, account.getBankId());
        pst.setLong(4, account.getFirmId());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            account.setId(rs.getLong(1));
        }
        rs.close();
        return account;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellAcc");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public Account edit(Account account) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editAcc");
        pst.setString(1, account.getNumber());
        pst.setLong(2, account.getCurrencyId());
        pst.setLong(3, account.getBankId());
        pst.setLong(4, account.getFirmId());
        pst.setLong(5, account.getId());
        if (pst.executeUpdate() > 0) {
            return account;
        } else {
            return null;
        }
    }

    @Override
    public Account get(Long id) throws SQLException {
        Account account = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getAcc");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            account = new Account();
            account.setId(rs.getLong("id"));
            account.setNumber(rs.getString("number"));
            account.setCurrencyId(rs.getLong("curr_id"));
           account.setBankId(rs.getLong("bank_id"));
           account.setFirmId(rs.getLong("firm_id"));
        }
        rs.close();
        return account;
    }
}
