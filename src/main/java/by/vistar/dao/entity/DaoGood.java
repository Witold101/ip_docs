package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Good;
import by.vistar.entity.Party;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoGood implements DaoImpl<Long, Good> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoGood INSTANCE = null;

    private DaoGood() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoGood getInstance() throws SQLException {
        DaoGood daoGood = INSTANCE;
        if (daoGood == null) {
            synchronized (DaoGood.class) {
                daoGood = INSTANCE;
                if (daoGood == null) {
                    INSTANCE = daoGood = new DaoGood();
                }
            }
        }
        return daoGood;
    }

    private void initPrepareStatement(Connection connection) throws SQLException {
        if (mysqlPrepareStatement == null) {
            mysqlPrepareStatement = new HashMap<>();
            mysqlPrepareStatement.put("addGood", connection.prepareStatement(MYSQL_ADD_GOOD, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getGood", connection.prepareStatement(MYSQL_GET_GOOD));
            mysqlPrepareStatement.put("editGood", connection.prepareStatement(MYSQL_EDIT_GOOD));
            mysqlPrepareStatement.put("dellGood", connection.prepareStatement(MYSQL_DELL_GOOD));
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Map<String, PreparedStatement> getMysqlPrepareStatement() {
        return mysqlPrepareStatement;
    }

    @Override
    public Good add(Good good) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addGood");
        pst.setString(1, good.getName());
        pst.setString(2, good.getFullName());
        pst.setString(3, good.getSqr());
        pst.setLong(4, good.getParty().getId());
        pst.setString(5, good.getNote());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            good.setId(rs.getLong(1));
        }
        rs.close();
        return good;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellGood");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public Good edit(Good good) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editGood");
        pst.setString(1, good.getName());
        pst.setString(2, good.getFullName());
        pst.setString(3, good.getSqr());
        pst.setLong(4, good.getParty().getId());
        pst.setString(5, good.getNote());
        pst.setLong(6, good.getId());
        if (pst.executeUpdate() > 0) {
            return good;
        } else {
            return null;
        }
    }

    @Override
    public Good get(Long id) throws SQLException {
        Good good = null;
                Long partyId = null;
                Party party = null;
                PreparedStatement pst = mysqlPrepareStatement.get("getGood");
                pst.setLong(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    good = new Good();
                good.setId(rs.getLong("id"));
                good.setName(rs.getString("name"));
                good.setFullName(rs.getString("full_name"));
                good.setSqr(rs.getString("sqr"));
                partyId=(rs.getLong("party"));
            }
        if (partyId > 0) {
            DaoParty daoParty = DaoParty.getInstance();
            party = daoParty.get(partyId);
            good.setParty(party);
        }
        rs.close();
        return good;
    }
}

