package by.vistar.services.entity;

import by.vistar.dao.dto.DtoCurrency;
import by.vistar.dao.entity.DaoCurrency;
import by.vistar.entity.Currency;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceCurrency extends ServiceSetup implements DaoServiceImpl<Long, Currency> {

    private DaoCurrency daoCurrency;
    private DtoCurrency dtoCurrency;

    public ServiceCurrency() {
        super();
        try {
            daoCurrency = DaoCurrency.getInstance();
            dtoCurrency = DtoCurrency.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
        super.setConnection(daoCurrency.getConnection());
    }

    @Override
    public Currency add(Currency currency) {
        if (currency != null) {
            startTransaction();
            try {
                daoCurrency.add(currency);
            } catch (SQLException e) {
                System.out.println("Error add CURRENCY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return currency;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoCurrency.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell CURRENCY from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (CURRENCY) == null");
        }
    }

    @Override
    public Currency edit(Currency currency) {
        if (currency != null) {
            startTransaction();
            try {
                daoCurrency.edit(currency);
            } catch (SQLException e) {
                System.out.println("Error edit CURRENCY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return currency;
    }

    @Override
    public Currency get(Long id) {
        Currency currency = null;
        if (id != null) {
            startTransaction();
            try {
                currency = daoCurrency.get(id);
            } catch (SQLException e) {
                System.out.println("Error get CURRENCY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return currency;
    }

    public Currency getWithCode(String code) {
        Currency currency = null;
        if (code != null){
            startTransaction();
            try {
                currency = dtoCurrency.getWithCode(code);
            } catch (SQLException e) {
                System.out.println("Error get CURRENCY WITH CODE FROM DB.");
                e.printStackTrace();
            }
            commit();
        }
        return currency;
    }
}
