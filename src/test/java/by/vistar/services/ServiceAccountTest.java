package by.vistar.services;

import by.vistar.db.DbInit;
import by.vistar.entity.Account;
import by.vistar.entity.Bank;
import by.vistar.entity.Currency;
import by.vistar.entity.Firm;
import by.vistar.services.entity.ServiceAccount;
import by.vistar.services.entity.ServiceBank;
import by.vistar.services.entity.ServiceCurrency;
import by.vistar.services.entity.ServiceFirm;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServiceAccountTest {

    @Before
    public void setUp() {
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

        Account accountTest = new ServiceAccount().get(account.getId());

        assertEquals(account.getId(),accountTest.getId());
        assertEquals(account.getBankId(),accountTest.getBank().getId());
        assertEquals(account.getCurrencyId(),accountTest.getCurrency().getId());
        assertEquals(account.getFirmId(),accountTest.getFirm().getId());

        new ServiceAccount().dell(account.getId());
        new ServiceBank().dell(bank.getId());
        new ServiceCurrency().dell(currency.getId());
        new ServiceFirm().dell(firm.getId());
    }

    @Test
    public void dell() {
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

        Account accountTest = new ServiceAccount().get(account.getId());
        assertEquals(account.getId(),accountTest.getId());

        new ServiceAccount().dell(account.getId());
        accountTest = new ServiceAccount().get(account.getId());
        assertNull(accountTest);

        new ServiceBank().dell(bank.getId());
        new ServiceCurrency().dell(currency.getId());
        new ServiceFirm().dell(firm.getId());
    }

    @Test
    public void edit() {

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

        account.setNumber("4751712622612168268 test");
        Account accountTest = new ServiceAccount().edit(account);

        assertEquals(account.getNumber(),accountTest.getNumber());

        new ServiceAccount().dell(account.getId());
        new ServiceBank().dell(bank.getId());
        new ServiceCurrency().dell(currency.getId());
        new ServiceFirm().dell(firm.getId());

    }

    @Test
    public void get() {
        add();
    }
}