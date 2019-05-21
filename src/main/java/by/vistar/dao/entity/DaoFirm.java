package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Firm;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoFirm implements DaoImpl<Long, Firm> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoFirm INSTANCE = null;

    private DaoFirm() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoFirm getInstance() throws SQLException {
        DaoFirm daoFirm = INSTANCE;
        if (daoFirm == null) {
            synchronized (DaoFirm.class) {
                daoFirm = INSTANCE;
                if (daoFirm == null) {
                    INSTANCE = daoFirm = new DaoFirm();
                }
            }
        }
        return daoFirm;
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
            mysqlPrepareStatement.put("addFirm", connection.prepareStatement(MYSQL_ADD_FIRM, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getFirm", connection.prepareStatement(MYSQL_GET_FIRM));
            mysqlPrepareStatement.put("editFirm", connection.prepareStatement(MYSQL_EDIT_FIRM));
            mysqlPrepareStatement.put("dellFirm", connection.prepareStatement(MYSQL_DELL_FIRM));
        }
    }

    @Override
    public Firm add(Firm firm) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addFirm");
        pst.setString(1, firm.getName());
        pst.setString(2, firm.getFullName());
        pst.setString(3, firm.getVatNumber());
        pst.setBoolean(4, firm.getMain());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            firm.setId(rs.getLong(1));
        }
        rs.close();
        return firm;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellFirm");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public Firm edit(Firm firm) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editFirm");
        pst.setString(1, firm.getName());
        pst.setString(2, firm.getFullName());
        pst.setString(3, firm.getVatNumber());
        pst.setBoolean(4, firm.getMain());
        pst.setLong(5, firm.getId());
        if (pst.executeUpdate() > 0) {
            return firm;
        } else {
            return null;
        }
    }

    @Override
    public Firm get(Long id) throws SQLException {
        Firm firm = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getFirm");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            firm = new Firm();
            firm.setId(rs.getLong("id"));
            firm.setName(rs.getString("name_firm"));
            firm.setFullName(rs.getString("full_name_firm"));
            firm.setVatNumber(rs.getString("vat_number"));
            firm.setMain(rs.getBoolean("main"));
        }
        rs.close();
        return firm;
    }
}
