package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.Party;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoParty implements DaoImpl<Long, Party> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoParty INSTANCE = null;

    private DaoParty() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoParty getInstance() throws SQLException {
        DaoParty daoParty = INSTANCE;
        if (daoParty == null) {
            synchronized (DaoParty.class) {
                daoParty = INSTANCE;
                if (daoParty == null) {
                    INSTANCE = daoParty = new DaoParty();
                }
            }
        }
        return daoParty;
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
            mysqlPrepareStatement.put("addParty", connection.prepareStatement(MYSQL_ADD_PARTY, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getParty", connection.prepareStatement(MYSQL_GET_PARTY));
            mysqlPrepareStatement.put("editParty", connection.prepareStatement(MYSQL_EDIT_PARTY));
            mysqlPrepareStatement.put("dellParty", connection.prepareStatement(MYSQL_DELL_PARTY));
        }
    }


    @Override
    public Party add(Party party) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addParty");
        pst.setString(1, party.getName());
        pst.setString(2, party.getNote());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            party.setId(rs.getLong(1));
        }
        rs.close();
        return party;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellParty");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public Party edit(Party party) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editParty");
        pst.setString(1, party.getName());
        pst.setString(2, party.getNote());
        if (pst.executeUpdate() > 0) {
            return party;
        } else {
            return null;
        }
    }

    @Override
    public Party get(Long id) throws SQLException {
        Party party = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getParty");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            party = new Party();
            party.setId(rs.getLong("id"));
            party.setName(rs.getString("name"));
            party.setNote(rs.getString("note"));
        }
        rs.close();
        return party;
    }
}
