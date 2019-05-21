package by.vistar.services.entity;

import by.vistar.dao.entity.DaoParty;
import by.vistar.entity.Party;
import by.vistar.services.interfaces.DaoServiceImpl;

import java.sql.SQLException;

public class ServiceParty extends ServiceSetup implements DaoServiceImpl<Long, Party> {

    private DaoParty daoParty;

    public ServiceParty() {
        super();
        try {
            daoParty = DaoParty.getInstance();
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
        super.setConnection(daoParty.getConnection());
    }

    @Override
    public Party add(Party party) {
        if (party != null) {
            startTransaction();
            try {
                daoParty.add(party);
            } catch (SQLException e) {
                System.out.println("Error add PARTY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return party;
    }

    @Override
    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoParty.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell PARTY from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id (PARTY) == null");
        }
    }

    @Override
    public Party edit(Party party) {
        if (party != null) {
            startTransaction();
            try {
                daoParty.edit(party);
            } catch (SQLException e) {
                System.out.println("Error edit PARTY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return party;
    }

    @Override
    public Party get(Long id) {
        Party party = null;
        if (id != null) {
            startTransaction();
            try {
                party = daoParty.get(id);
            } catch (SQLException e) {
                System.out.println("Error get PARTY in DB.");
                e.printStackTrace();
            }
            commit();
        }
        return party;
    }
}
