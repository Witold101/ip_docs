package by.vistar.services.entity;

import by.vistar.dao.entity.DaoListBaySell;
import by.vistar.entity.ListBaySell;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceListBaySell extends ServiceSetup implements DaoServiceImpl<Long, ListBaySell> {

    private DaoListBaySell daoListBaySell;

    public ServiceListBaySell() {
        super();
        try {
            daoListBaySell = DaoListBaySell.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement SERVICE LIST BAY SELL");
            e.printStackTrace();
        }
        super.setConnection(daoListBaySell.getConnection());
    }

    @Override
    public ListBaySell add(ListBaySell listBaySell) {
        if (listBaySell != null) {
            startTransaction();
            try {
                daoListBaySell.add(listBaySell);
            } catch (SQLException e) {
                System.out.println("Error add LIST BAY SELL in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return listBaySell;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoListBaySell.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell LIST BAY SELL from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (GOOD) == null");
        }
    }

    @Override
    public ListBaySell edit(ListBaySell listBaySell) {
        if (listBaySell != null) {
            startTransaction();
            try {
                daoListBaySell.edit(listBaySell);
            } catch (SQLException e) {
                System.out.println("Error edit LIST BAY SELL in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return listBaySell;
    }

    @Override
    public ListBaySell get(Long id) {
        ListBaySell listBaySell = null;
        if (id != null) {
            startTransaction();
            try {
                listBaySell = daoListBaySell.get(id);
            } catch (SQLException e) {
                System.out.println("Error get LIST BAY SELL in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return listBaySell;
    }
}
