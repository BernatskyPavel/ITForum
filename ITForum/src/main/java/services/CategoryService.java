package services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Category;

@ApplicationScoped
public class CategoryService implements IService<Category> {
    @Inject
    private DbService DbManager;
    @Override
    public List<Category> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Category getById(Object Id) {
	Category category = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<Category> query = manager.
		      createNamedQuery("Category.findById", Category.class);
	query.setParameter("id", ((Integer)Id).intValue());
	try {
	    category = query.getSingleResult();
	} catch (Exception e) {
	    category = null;
	}
	manager.getTransaction().commit();
	manager.close();
	return category;
    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Category create(Category entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Category update(Category entity) {
	// TODO Auto-generated method stub
	return null;
    }

}
