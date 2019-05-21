package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Currency;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoCurrency implements DaoImpl<Long, Currency> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoCurrency INSTANCE = null;

    private DaoCurrency() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoCurrency getInstance() throws SQLException {
        DaoCurrency daoCurrency = INSTANCE;
        if (daoCurrency == null) {
            synchronized (DaoCurrency.class) {
                daoCurrency = INSTANCE;
                if (daoCurrency == null) {
                    INSTANCE = daoCurrency = new DaoCurrency();
                }
            }
        }
        return daoCurrency;
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
            mysqlPrepareStatement.put("addCurrency", connection.prepareStatement(MYSQL_ADD_CURRENCY, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getCurrency", connection.prepareStatement(MYSQL_GET_CURRENCY));
            mysqlPrepareStatement.put("editCurrency", connection.prepareStatement(MYSQL_EDIT_CURRENCY));
            mysqlPrepareStatement.put("dellCurrency", connection.prepareStatement(MYSQL_DELL_CURRENCY));
        }
    }
    @Override
    public Currency add(Currency currency) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addCurrency");
        pst.setString(1,currency.getCodeIso4217());
        pst.setString(2, currency.getCodeNumberIso4217());
        pst.setString(3, currency.getInfo());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            currency.setId(rs.getLong(1));
        }
        rs.close();
        return currency;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellCurrency");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public Currency edit(Currency currency) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editCurrency");
        pst.setString(1, currency.getCodeIso4217());
        pst.setString(2, currency.getCodeNumberIso4217());
        pst.setString(3, currency.getInfo());
        pst.setLong(4,currency.getId());
        if (pst.executeUpdate() > 0) {
            return currency;
        } else {
            return null;
        }
    }

    @Override
    public Currency get(Long id) throws SQLException {
        Currency currency = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getCurrency");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            currency = new Currency();
            currency.setId(rs.getLong("id"));
            currency.setCodeIso4217(rs.getString("code_iso4217"));
            currency.setCodeNumberIso4217(rs.getString("code_iso4217_numb"));
            currency.setInfo(rs.getString("curr_code_info"));
        }
        rs.close();
        return currency;
    }
}
