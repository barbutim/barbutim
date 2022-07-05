package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.Category;
import cz.cvut.fel.nss.careerpages.model.Offer;
import org.springframework.stereotype.Repository;

/**
 * dao for categories
 */
@Repository
public class CategoryDao extends BaseDao<Category>{
    protected CategoryDao() {
        super(Category.class);
    }

    public boolean add(Category category, Offer trip){
        return category.add(trip);
    }
}