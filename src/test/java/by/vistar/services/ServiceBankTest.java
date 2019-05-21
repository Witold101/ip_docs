package by.vistar.services;

import by.vistar.db.DbInit;
import by.vistar.entity.Bank;
import by.vistar.services.entity.ServiceBank;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServiceBankTest {

    @Before
    public void setUp() throws Exception {
        try {
            DbInit.initTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() {
        Bank bank = new Bank();
        bank.setName("РРБ Банк");
        bank.setAddressId(1L);
        bank.setSwift("34ER555GG");
        new ServiceBank().add(bank);
        Bank bankTest = new ServiceBank().get(bank.getId());
        assertEquals(bank.getId(), bankTest.getId());
        assertEquals(bank.getName(), bankTest.getName());
        assertEquals(bank.getSwift(), bankTest.getSwift());
        assertEquals(bank.getAddressId(), bankTest.getAddressId());
        new ServiceBank().dell(bank.getId());
    }

    @Test
    public void dell() {
        Bank bank = new Bank();
        bank.setName("РРБ Банк");
        bank.setAddressId(1L);
        bank.setSwift("34ER555GG");
        new ServiceBank().add(bank);
        Bank bankTest = new ServiceBank().get(bank.getId());
        assertEquals(bank.getId(), bankTest.getId());
        new ServiceBank().dell(bank.getId());
        bankTest = new ServiceBank().get(bank.getId());
        assertNull(bankTest);
    }

    @Test
    public void edit() {
        Bank bank = new Bank();
        bank.setName("РРБ Банк");
        bank.setAddressId(1L);
        bank.setSwift("34ER555GG");
        new ServiceBank().add(bank);
        Bank bankTest = new ServiceBank().get(bank.getId());
        bankTest.setName("РРБ Банк test");
        bankTest.setAddressId(2L);
        bankTest.setSwift("34ER555GG test");
        new ServiceBank().edit(bankTest);
        bank = new ServiceBank().get(bankTest.getId());
        assertEquals(bank.getId(),bankTest.getId());
        assertEquals(bank.getName(),bankTest.getName());
        assertEquals(bank.getSwift(),bankTest.getSwift());
        assertEquals(bank.getAddressId(),bankTest.getAddressId());
        new ServiceBank().dell(bankTest.getId());

    }

    @Test
    public void get() {
        add();
    }
}