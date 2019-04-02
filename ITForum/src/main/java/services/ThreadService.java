package services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Thread;

@Named("ThreadService")
@ApplicationScoped
public class ThreadService implements IService<Thread> {
    @Inject
    private DbService DbManager;
    @Override
    public List<Thread> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Thread getById(Object Id) {
	Thread category = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<Thread> query = manager.
		      createNamedQuery("Thread.findById", Thread.class);
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
    public void create(Thread entity) {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(Thread entity) {
	// TODO Auto-generated method stub

    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub

    }

}
