package by.vistar.services.entity;

import by.vistar.dao.entity.DaoBank;
import by.vistar.entity.Bank;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceBank extends ServiceSetup implements DaoServiceImpl<Long, Bank> {

    private DaoBank daoBank;

    public ServiceBank() {
        super();
        try {
            daoBank = DaoBank.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
        super.setConnection(daoBank.getConnection());
    }

    @Override
    public Bank add(Bank bank) {
        if (bank != null) {
            startTransaction();
            try {
                daoBank.add(bank);
            } catch (SQLException e) {
                System.out.println("Error add BANK in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return bank;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoBank.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell BANK from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (BANK) == null");
        }
    }

    @Override
    public Bank edit(Bank bank) {
        if (bank != null) {
            startTransaction();
            try {
                daoBank.edit(bank);
            } catch (SQLException e) {
                System.out.println("Error edit BANK in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return bank;
    }

    @Override
    public Bank get(Long id) {
        Bank bank = null;
        if (id != null) {
            startTransaction();
            try {
                bank = daoBank.get(id);
            } catch (SQLException e) {
                System.out.println("Error get BANK in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return bank;
    }
}
