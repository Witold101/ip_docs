package by.vistar.services.entity;

import by.vistar.dao.entity.DaoPay;
import by.vistar.entity.Pay;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServicePay extends ServiceSetup implements DaoServiceImpl<Long, Pay> {

    private DaoPay daoPay;

    public ServicePay() {
        super();
        try {
            daoPay = DaoPay.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
        super.setConnection(daoPay.getConnection());
    }

    @Override
    public Pay add(Pay pay) {
        if (pay != null) {
            startTransaction();
            try {
                daoPay.add(pay);
            } catch (SQLException e) {
                System.out.println("Error add PAY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return pay;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoPay.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell PAY from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (PAY) == null");
        }
    }

    @Override
    public Pay edit(Pay pay) {
        if (pay != null) {
            startTransaction();
            try {
                daoPay.edit(pay);
            } catch (SQLException e) {
                System.out.println("Error edit PAY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return pay;
    }

    @Override
    public Pay get(Long id) {
        Pay pay = null;
        if (id != null) {
            startTransaction();
            try {
                pay = daoPay.get(id);
                if (pay.getCurrencyId()>0){
                    pay.setCurrency(new ServiceCurrency().get(pay.getCurrencyId()));
                }
                if (pay.getPayerId()>0){
                    pay.setPayer(new ServiceFirm().get(pay.getPayerId()));
                }
                if (pay.getPayerAccId()>0){
                    pay.setPayerAcc(new ServiceAccount().get(pay.getPayerAccId()));
                }
            } catch (SQLException e) {
                System.out.println("Error get PAY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return pay;
    }
}
