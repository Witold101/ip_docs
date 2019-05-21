package by.vistar.services;

import by.vistar.db.DbInit;
import by.vistar.entity.Firm;
import by.vistar.services.entity.ServiceFirm;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServiceFirmTest {

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
        Firm firmTest = new ServiceFirm().get(firm.getId());
        assertEquals(firm.getId(), firmTest.getId());
        assertEquals(firm.getName(), firmTest.getName());
        assertEquals(firm.getFullName(), firmTest.getFullName());
        assertEquals(firm.getVatNumber(), firmTest.getVatNumber());
        assertEquals(firm.getMain(), firmTest.getMain());
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
        Long count = new ServiceFirm().getCountRow();
        assertEquals(count.longValue(), 1L);
        new ServiceFirm().dell(firm.getId());
        count = new ServiceFirm().getCountRow();
        assertEquals(count.longValue(), 0L);
    }

    @Test
    public void edit() {
        Firm firm = new Firm();
        firm.setName("Частное предприятие \"Вистал\"");
        firm.setFullName("Частное торговое унитарное предприятие \"Вистал\"");
        firm.setVatNumber("1245RR545");
        firm.setMain(true);

        new ServiceFirm().add(firm);

        Firm firmTest = new Firm();
        firmTest.setId(firm.getId());
        firmTest.setName("Частное предприятие \"Вистал\" test");
        firmTest.setFullName("Частное торговое унитарное предприятие \"Вистал\" test");
        firmTest.setVatNumber("1245RR545 test");
        firmTest.setMain(false);

        new ServiceFirm().edit(firmTest);

        firm = new ServiceFirm().get(firmTest.getId());

        assertEquals(firm.getId(), firmTest.getId());
        assertEquals(firm.getName(), firmTest.getName());
        assertEquals(firm.getFullName(), firmTest.getFullName());
        assertEquals(firm.getVatNumber(), firmTest.getVatNumber());
        assertEquals(firm.getMain(), firmTest.getMain());

        new ServiceFirm().dell(firm.getId());
    }

    @Test
    public void get() {
        add();
    }

    @Test
    public void getMainId() {
        Firm firm = new Firm();
        firm.setName("Частное предприятие \"Вистал\"");
        firm.setFullName("Частное торговое унитарное предприятие \"Вистал\"");
        firm.setVatNumber("1245RR545");
        firm.setMain(true);
        new ServiceFirm().add(firm);
        Long mainId = new ServiceFirm().getMainId();
        assertNotNull(mainId);
        new ServiceFirm().dell(firm.getId());
        mainId = new ServiceFirm().getMainId();
        assertNull(mainId);
    }

    @Test
    public void getCountRow() {
        Firm firm = new Firm();
        firm.setName("Частное предприятие \"Вистал\"");
        firm.setFullName("Частное торговое унитарное предприятие \"Вистал\"");
        firm.setVatNumber("1245RR545");
        firm.setMain(true);
        Long countRows_1 = new ServiceFirm().getCountRow();
        new ServiceFirm().add(firm);
        Long countRows_2 = new ServiceFirm().getCountRow();
        countRows_1++;
        assertEquals(countRows_1,countRows_2);
        new ServiceFirm().dell(firm.getId());
    }
}