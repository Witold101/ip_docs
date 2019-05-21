package by.vistar.services.entity;

import by.vistar.dao.entity.DaoUser;
import by.vistar.entity.User;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceUser extends ServiceSetup implements DaoServiceImpl<Long, User> {

    private DaoUser daoUser;

    public ServiceUser() {
        super();
        try {
            daoUser = DaoUser.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
        super.setConnection(daoUser.getConnection());
    }


    @Override
    public User add(User user) {
        if (user != null) {
            startTransaction();
            try {
                daoUser.add(user);
            } catch (SQLException e) {
                System.out.println("Error add CLIENT in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return user;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoUser.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell USER from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (USER) == null");
        }
    }

    @Override
    public User edit(User user) {
        if (user != null) {
            startTransaction();
            try {
                daoUser.edit(user);
            } catch (SQLException e) {
                System.out.println("Error edit USER in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return user;
    }

    @Override
    public User get(Long id) {
        User user = null;
        if (id != null) {
            startTransaction();
            try {
                user = daoUser.get(id);
                if (user.getFirmId()>0) {
                    user.setFirm(new ServiceFirm().get(user.getFirmId()));
                }
            } catch (SQLException e) {
                System.out.println("Error get USER in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return user;
    }
}
