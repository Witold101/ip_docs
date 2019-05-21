package by.vistar.dao.entity;

import by.vistar.dao.interfaces.DaoImpl;
import by.vistar.entity.Address;

import java.sql.SQLException;

/**
 * Класс не реализован
 */

public class DaoAddress implements DaoImpl<Long, Address> {
    @Override
    public Address add(Address address) throws SQLException {
        return null;
    }

    @Override
    public void dell(Long id) throws SQLException {

    }

    @Override
    public Address edit(Address address) throws SQLException {
        return null;
    }

    @Override
    public Address get(Long id) throws SQLException {
        return null;
    }
}
