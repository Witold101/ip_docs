package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.db.DbConnection;
import by.vistar.entity.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static by.vistar.db.DbConstants.*;

public class DaoUser implements DaoImpl<Long, User> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;
    private Connection connection;

    private static volatile DaoUser INSTANCE = null;

    private DaoUser() throws SQLException {
        this.connection = DbConnection.getConnection();
        initPrepareStatement(connection);
    }

    public static DaoUser getInstance() throws SQLException {
        DaoUser daoUser = INSTANCE;
        if (daoUser == null) {
            synchronized (DaoUser.class) {
                daoUser = INSTANCE;
                if (daoUser == null) {
                    INSTANCE = daoUser = new DaoUser();
                }
            }
        }
        return daoUser;
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
            mysqlPrepareStatement.put("addUser", connection.prepareStatement(MYSQL_ADD_USER, Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getUser", connection.prepareStatement(MYSQL_GET_USER));
            mysqlPrepareStatement.put("editUser", connection.prepareStatement(MYSQL_EDIT_USER));
            mysqlPrepareStatement.put("dellUser", connection.prepareStatement(MYSQL_DELL_USER));
        }
    }


    @Override
    public User add(User user) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addUser");
        pst.setString(1, user.getName());
        pst.setString(2, user.getFullName());
        pst.setLong(3, user.getFirmId());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getLong(1));
        }
        rs.close();
        return user;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellUser");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public User edit(User user) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editUser");
        pst.setString(1, user.getName());
        pst.setString(2, user.getFullName());
        pst.setLong(3, user.getFirmId());
        if (pst.executeUpdate() > 0) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User get(Long id) throws SQLException {
        User user = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getUser");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setFullName(rs.getString("full_name"));
            user.setFirmId(rs.getLong("id_firm"));
        }
        rs.close();
        return user;
    }
}
