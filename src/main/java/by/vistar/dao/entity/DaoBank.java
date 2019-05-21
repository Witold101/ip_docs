package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Bank;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoBank implements DaoImpl<Long, Bank> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoBank INSTANCE = null;

    private DaoBank() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoBank getInstance() throws SQLException {
        DaoBank daoBank = INSTANCE;
        if (daoBank == null) {
            synchronized (DaoBank.class) {
                daoBank = INSTANCE;
                if (daoBank == null) {
                    INSTANCE = daoBank = new DaoBank();
                }
            }
        }
        return daoBank;
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
            mysqlPrepareStatement.put("addBank", connection.prepareStatement(MYSQL_ADD_BANK, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getBank", connection.prepareStatement(MYSQL_GET_BANK));
            mysqlPrepareStatement.put("editBank", connection.prepareStatement(MYSQL_EDIT_BANK));
            mysqlPrepareStatement.put("dellBank", connection.prepareStatement(MYSQL_DELL_BANK));
        }
    }

    @Override
    public Bank add(Bank bank) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addBank");
        pst.setString(1,bank.getSwift());
        pst.setString(2, bank.getName());
        pst.setLong(3, bank.getAddressId());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            bank.setId(rs.getLong(1));
        }
        rs.close();
        return bank;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellBank");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public Bank edit(Bank bank) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editBank");
        pst.setString(1, bank.getSwift());
        pst.setString(2, bank.getName());
        pst.setLong(3, bank.getAddressId());
        pst.setLong(4, bank.getId());
        if (pst.executeUpdate() > 0) {
            return bank;
        } else {
            return null;
        }
    }

    @Override
    public Bank get(Long id) throws SQLException {
        Bank bank = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getBank");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            bank = new Bank();
            bank.setId(rs.getLong("id"));
            bank.setSwift(rs.getString("bic"));
            bank.setName(rs.getString("name_bank"));
            bank.setAddressId(rs.getLong("address_id"));
        }
        rs.close();
        return bank;
    }
}
