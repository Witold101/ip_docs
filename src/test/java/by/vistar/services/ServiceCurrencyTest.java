package by.vistar.services;

import by.vistar.db.DbInit;
import by.vistar.entity.Currency;
import by.vistar.services.entity.ServiceCurrency;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServiceCurrencyTest {

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
        Currency currency = new Currency();
        currency.setCodeIso4217("BYY");
        currency.setCodeNumberIso4217("933");
        currency.setInfo("Белорусский рубль");
        new ServiceCurrency().add(currency);
        Currency currencyTest = new ServiceCurrency().get(currency.getId());
        assertEquals(currency.getId(),currencyTest.getId());
        assertEquals(currency.getCodeIso4217(),currencyTest.getCodeIso4217());
        assertEquals(currency.getCodeNumberIso4217(),currencyTest.getCodeNumberIso4217());
        assertEquals(currency.getInfo(),currencyTest.getInfo());
        new ServiceCurrency().dell(currency.getId());
    }

    @Test
    public void dell() {
        Currency currency = new Currency();
        currency.setCodeIso4217("BYY");
        currency.setCodeNumberIso4217("933");
        currency.setInfo("Белорусский рубль");
        new ServiceCurrency().add(currency);
        Currency currencyTest = new ServiceCurrency().get(currency.getId());
        assertEquals(currency.getId(),currencyTest.getId());
        new ServiceCurrency().dell(currencyTest.getId());
        currencyTest = new ServiceCurrency().get(currency.getId());
        assertNull(currencyTest);
    }

    @Test
    public void edit() {
        Currency currency = new Currency();
        currency.setCodeIso4217("BYY");
        currency.setCodeNumberIso4217("933");
        currency.setInfo("Белорусский рубль");
        new ServiceCurrency().add(currency);
        currency.setCodeIso4217("BYN");
        currency.setCodeNumberIso4217("999");
        currency.setInfo("Белорусский рубль test");
        new ServiceCurrency().edit(currency);
        Currency currencyTest = new ServiceCurrency().get(currency.getId());
        assertEquals(currency.getId(),currencyTest.getId());
        assertEquals(currency.getCodeIso4217(),currencyTest.getCodeIso4217());
        assertEquals(currency.getCodeNumberIso4217(),currencyTest.getCodeNumberIso4217());
        assertEquals(currency.getInfo(),currencyTest.getInfo());
        new ServiceCurrency().dell(currency.getId());
    }

    @Test
    public void get() {
        add();
    }

    @Test
    public void getWithCode() {
        Currency currency = new Currency();
        currency.setCodeIso4217("BYY");
        currency.setCodeNumberIso4217("933");
        currency.setInfo("Белорусский рубль");
        new ServiceCurrency().add(currency);
        Currency currencyTest = new ServiceCurrency().getWithCode(currency.getCodeNumberIso4217());
        assertEquals(currency.getId(),currencyTest.getId());
        assertEquals(currency.getCodeIso4217(),currencyTest.getCodeIso4217());
        assertEquals(currency.getCodeNumberIso4217(),currencyTest.getCodeNumberIso4217());
        assertEquals(currency.getInfo(),currencyTest.getInfo());
        new ServiceCurrency().dell(currency.getId());
    }
}