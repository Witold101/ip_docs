package by.vistar.services;

import by.vistar.db.DbInit;
import by.vistar.entity.Good;
import by.vistar.entity.ListBaySell;
import by.vistar.entity.Party;
import by.vistar.services.entity.ServiceGood;
import by.vistar.services.entity.ServiceListBaySell;
import by.vistar.services.entity.ServiceParty;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class ServiceListBaySellTest {

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
        Party party = new Party();
        party.setName("PLD 4132");
        party.setNote("Now");

        party = new ServiceParty().add(party);

        Good good = new Good();
        good.setName("Подноски 101/7");
        good.setFullName("Подносок металлический с резиновой полосой 101/7");
        good.setSqr("Pair");
        good.setParty(party);
        good.setNote("Now");

        good = new ServiceGood().add(good);

        ListBaySell listBaySell = new ListBaySell();
        listBaySell.setNumber("NT 28987");
        listBaySell.setDate(new Date());
        listBaySell.setGood(good);
        listBaySell.setQuantity(1.259F);
        listBaySell.setSum(12929282L);
        listBaySell.setBay(true);

        listBaySell = new ServiceListBaySell().add(listBaySell);


        ListBaySell listBaySell2= new ServiceListBaySell().get(listBaySell.getId());
        assertEquals(listBaySell.getId(),listBaySell2.getId());
        assertEquals(listBaySell.getNumber(),listBaySell2.getNumber());
        assertEquals(listBaySell.getGood().getId(),listBaySell2.getGood().getId());
        assertEquals(listBaySell.getQuantity(),listBaySell2.getQuantity());
        assertEquals(listBaySell.getSum(),listBaySell2.getSum());
        assertEquals(listBaySell.getBay(),listBaySell2.getBay());
    }

    @Test
    public void dell() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void get() {
        add();
    }
}