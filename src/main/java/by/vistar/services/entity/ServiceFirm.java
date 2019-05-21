package by.vistar.services.entity;

import by.vistar.dao.dto.DtoFirm;
import by.vistar.dao.entity.DaoFirm;
import by.vistar.entity.Firm;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceFirm extends ServiceSetup implements DaoServiceImpl<Long, Firm> {
    private DaoFirm daoFirm;
    private DtoFirm dtoFirm;

    public ServiceFirm() {
        super();
        try {
            daoFirm = DaoFirm.getInstance();
            dtoFirm = DtoFirm.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
        super.setConnection(daoFirm.getConnection());
    }


    @Override
    public Firm add(Firm firm) {
        startTransaction();
        try {
            daoFirm.add(firm);
        } catch (SQLException e) {
            System.out.println("Error add FIRM in DB.");
            e.printStackTrace();
        }
        commit();
        return firm;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoFirm.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell FIRM from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (FIRM) == null");
        }
    }

    @Override
    public Firm edit(Firm firm) {
        if (firm != null) {
            startTransaction();
            try {
                daoFirm.edit(firm);
            } catch (SQLException e) {
                System.out.println("Error edit FIRM in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return firm;
    }

    @Override
    public Firm get(Long id) {
        Firm firm = null;
        if (id != null) {
            startTransaction();
            try {
                firm = daoFirm.get(id);
            } catch (SQLException e) {
                System.out.println("Error get FIRM from DB.");
                e.printStackTrace();
            }
            commit();
        }
        return firm;
    }

    public Long getMainId() {
        Long id = null;
        startTransaction();
        try {
            id = dtoFirm.getMainId();
        } catch (SQLException e) {
            System.out.println("Error get MAIN ID from DB.");
            e.printStackTrace();
        }
        commit();
        return id;
    }

    public Long getCountRow() {
        Long rows = 0L;
        startTransaction();
        try {
            rows = dtoFirm.getCountRow();
        } catch (SQLException e) {
            System.out.println("Error get COUNTS ROW from DB.");
            e.printStackTrace();
        }
        commit();
        return rows;
    }
}
