package by.vistar.services.entity;

import by.vistar.dao.entity.DaoGood;
import by.vistar.entity.Good;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceGood extends ServiceSetup implements DaoServiceImpl<Long, Good> {

    private DaoGood daoGood;

    public ServiceGood() {
        super();
        try {
            daoGood = DaoGood.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement SERVICE GOOD");
            e.printStackTrace();
        }
        super.setConnection(daoGood.getConnection());
    }
    @Override
    public Good add(Good good) {
        if (good != null) {
            startTransaction();
            try {
                daoGood.add(good);
            } catch (SQLException e) {
                System.out.println("Error add GOOD in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return good;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoGood.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell GOOD from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (GOOD) == null");
        }
    }

    @Override
    public Good edit(Good good) {
        if (good != null) {
            startTransaction();
            try {
                daoGood.edit(good);
            } catch (SQLException e) {
                System.out.println("Error edit GOOD in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return good;
    }

    @Override
    public Good get(Long id) {
        Good good = null;
        if (id != null) {
            startTransaction();
            try {
                good = daoGood.get(id);
            } catch (SQLException e) {
                System.out.println("Error get GOOD in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return good;
    }
}
