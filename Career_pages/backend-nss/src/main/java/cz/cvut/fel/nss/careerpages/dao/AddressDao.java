package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.Address;
import org.springframework.stereotype.Repository;

/**
 * Dao for Address - street, number, city, state...
 */
@Repository
public class AddressDao extends BaseDao<Address> {

    /**
     * constructor
     */
    public AddressDao() {

        super(Address.class);
    }
}
