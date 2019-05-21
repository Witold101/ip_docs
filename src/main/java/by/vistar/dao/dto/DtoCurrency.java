package by.vistar.dao.dto;

import by.vistar.db.DbConnection;
import by.vistar.entity.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.MYSQL_GET_CURR_ID_WITH_CODE;

public class DtoCurrency {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DtoCurrency INSTANCE = null;

    private DtoCurrency() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DtoCurrency getInstance() throws SQLException {
        DtoCurrency dtoCurrency = INSTANCE;
        if (dtoCurrency == null) {
            synchronized (DtoCurrency.class) {
                dtoCurrency = INSTANCE;
                if (dtoCurrency == null) {
                    INSTANCE = dtoCurrency = new DtoCurrency();
                }
            }
        }
        return dtoCurrency;
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
            mysqlPrepareStatement.put("getCurrIdWithCode", connection.prepareStatement(MYSQL_GET_CURR_ID_WITH_CODE));
        }
    }

    public Currency getWithCode(String code) throws SQLException {
        Currency currency = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getCurrIdWithCode");
        pst.setString(1, code);
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
