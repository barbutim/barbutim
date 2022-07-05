package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.Offer;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Work offer dao
 */
@Repository
public class WorkOfferDao extends BaseDao<Offer> {
    /**
     * constructor
     */
    public WorkOfferDao() {
        super(Offer.class);
    }

    /**
     * @param id
     * @return query
     * find - id
     */
    public Offer find(String id){
        {
            try {
                return em.createNamedQuery("Offer.findByStringId", Offer.class).setParameter("id", id)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    /**
     * @param required_level
     * @return query
     * find - level
     */
    public List<Offer> find(int required_level){

        {
            try {
                return em.createNamedQuery("Offer.findByLevel", Offer.class).setParameter("required_level", required_level)
                        .getResultList();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    /**
     * @param location
     * @param from_date
     * @param to_date
     * @param minPrice
     * @param search
     * @return query
     * find - filter of above attributes
     */
    public List<Offer> findByFilter(String location, LocalDate from_date, LocalDate to_date, Double minPrice, String[] search){

        try {
                List<Offer> filteredTrips = new ArrayList<>();
                filteredTrips = em.createNamedQuery("Offer.findByFilter", Offer.class)
                        .setParameter("location", location)
                        .setParameter("from_date", from_date)
                        .setParameter("to_date", to_date)
                        .setParameter("minPrice", minPrice)
                        .getResultList();

                List<Long> ids = new ArrayList<>();
                for (Offer t : filteredTrips){
                    ids.add(t.getId());
                }

                if(search != null) {
                    String pattern = null;
                    filteredTrips = null;
                    for (String s : search) {
                        pattern = "%" + s + "%";

                        List<Offer> filteredTrips2 = em.createNamedQuery("Offer.findByPattern", Offer.class)
                                .setParameter("ids", ids)
                                .setParameter("pattern", pattern)
                                .getResultList();

                        if (filteredTrips2 != null) {
                            if (filteredTrips == null) filteredTrips = filteredTrips2;
                            else filteredTrips.addAll(filteredTrips2);
                        }
                    }
                }
                return filteredTrips;
        } catch (NoResultException e) {
                return null;
        }
    }
}