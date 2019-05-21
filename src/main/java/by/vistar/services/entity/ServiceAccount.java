package by.vistar.services.entity;

import by.vistar.dao.entity.DaoAccount;
import by.vistar.entity.Account;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceAccount extends ServiceSetup implements DaoServiceImpl<Long, Account> {

    private DaoAccount daoAccount;

    public ServiceAccount() {
        super();
        try {
            daoAccount = DaoAccount.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
        super.setConnection(daoAccount.getConnection());
    }

    @Override
    public Account add(Account account) {
        if (account != null) {
            startTransaction();
            try {
                daoAccount.add(account);
            } catch (SQLException e) {
                System.out.println("Error add ACCOUNT in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return account;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoAccount.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell ACCOUNT from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (ACCOUNT) == null");
        }
    }

    @Override
    public Account edit(Account account) {
        if (account != null) {
            startTransaction();
            try {
                daoAccount.edit(account);
            } catch (SQLException e) {
                System.out.println("Error edit ACCOUNT in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return account;
    }

    @Override
    public Account get(Long id) {
        Account account = null;
        if (id != null) {
            startTransaction();
            try {
                account = daoAccount.get(id);
                if (account!=null) {
                    account.setBank(new ServiceBank().get(account.getBankId()));
                    account.setCurrency(new ServiceCurrency().get(account.getCurrencyId()));
                    account.setFirm(new ServiceFirm().get(account.getFirmId()));
                }
            } catch (SQLException e) {
                System.out.println("Error get ACCOUNT in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return account;
    }
}
