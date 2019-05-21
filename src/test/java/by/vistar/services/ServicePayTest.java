package by.vistar.services;

import by.vistar.db.DbInit;
import by.vistar.entity.*;
import by.vistar.services.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class ServicePayTest {

    @Before
    public void initTables(){
        try {
            DbInit.initTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() {
        Firm firm = new Firm();
        firm.setName("Частное предприятие \"Вистал\"");
        firm.setFullName("Частное торговое унитарное предприятие \"Вистал\"");
        firm.setVatNumber("1245RR545");
        firm.setMain(true);
        new ServiceFirm().add(firm);

        Currency currency = new Currency();
        currency.setCodeIso4217("BYY");
        currency.setCodeNumberIso4217("933");
        currency.setInfo("Белорусский рубль");
        new ServiceCurrency().add(currency);

        Bank bank = new Bank();
        bank.setName("РРБ Банк");
        bank.setAddressId(1L);
        bank.setSwift("34ER555GG");
        new ServiceBank().add(bank);

        Account account = new Account();
        account.setNumber("4751712622612168268");
        account.setFirmId(firm.getId());
        account.setCurrencyId(currency.getId());
        account.setBankId(bank.getId());
        new ServiceAccount().add(account);

        Pay pay = new Pay();
        pay.setNumber("777");
        pay.setDate(LocalDateTime.now());
        pay.setPayerId(firm.getId());
        pay.setCurrencyId(currency.getId());
        pay.setPayerAccId(account.getId());
        pay.setSum(65433L);
        pay.setInfo("Оплата за попки");
        pay.setDebetDoc(true);
        pay.setCurrency(currency);
        pay.setPayer(firm);
        pay.setPayerAcc(account);

        new ServicePay().add(pay);
        Pay payTest = new ServicePay().get(pay.getId());

        assertEquals(pay.getId(),payTest.getId());
        assertEquals(pay.getCurrencyId(),payTest.getCurrencyId());
        assertEquals(pay.getPayerAccId(),payTest.getPayerAccId());
        assertEquals(pay.getPayerId(),payTest.getPayerId());
        assertEquals(pay.getDate().format(DateTimeFormatter.ISO_DATE),payTest.getDate().format(DateTimeFormatter.ISO_DATE));
        assertEquals(pay.getDebetDoc(),payTest.getDebetDoc());
        assertEquals(pay.getInfo(),payTest.getInfo());
        assertEquals(pay.getNumber(),payTest.getNumber());
        assertEquals(pay.getSum(),payTest.getSum());

        new ServicePay().dell(pay.getId());
        new ServiceAccount().dell(account.getId());
        new ServiceBank().dell(bank.getId());
        new ServiceCurrency().dell(currency.getId());
        new ServiceFirm().dell(firm.getId());
    }

    @Test
    public void dell() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void get() {
    }
}