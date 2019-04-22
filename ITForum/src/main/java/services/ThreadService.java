package services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Status;
import model.Thread;

@ApplicationScoped
public class ThreadService implements IService<Thread> {
    @Inject
    private DbService DbManager;
    @Inject
    private StatusService statusService;

    @Override
    public List<Thread> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    public void sync(Thread entity) {
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	try {
	    manager.persist(entity);
	    manager.persist(entity);
	} catch (Exception e) {
	    System.err.println(e.getLocalizedMessage());
	    manager.getTransaction().rollback();
	    manager.close();
	    return;
	}
	manager.getTransaction().commit();
	manager.close();
    }

    @Override
    public Thread getById(Object Id) {
	Thread category = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<Thread> query = manager
		.createNamedQuery("Thread.findById", Thread.class);
	query.setParameter("id", ((Integer) Id).intValue());
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
    public Thread create(Thread entity) {
	Status status = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();

	status = statusService
		.getById(StatusService.Statuses.APPROVED.getId());

	if (status == null) {
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}

	status.addThread(entity);
	entity.setStatusBean(status);

	// entity.setThread(thread);

	try {
	    manager.persist(entity);
	    manager.flush();
	    manager.refresh(entity);
	    entity.getCategoryBean().addThread(entity);
	    entity.getUserAccount().addThread(entity);
	    manager.merge(entity.getCategoryBean());
	    manager.merge(entity.getStatusBean());
	    manager.merge(entity.getUserAccount());
	} catch (Exception e) {
	    e.printStackTrace();
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	manager.flush();
	manager.getTransaction().commit();
	manager.close();
	return entity;
    }

    public Thread changeStatus(Thread entity, int sid) {
	Status status = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();

	status = statusService.getById(sid);

	if (status == null) {
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	try {
	    
	    entity.getStatusBean().removeThread(entity);
	    entity.setStatusBean(status);
	    entity.getStatusBean().addThread(entity);
	    manager.merge(entity);
	    manager.flush();
	    /*
	     * Query query =
	     * manager.createNativeQuery("UPDATE public.threads SET status = ? WHERE id = ?"
	     * ); query.setParameter(1, sid); query.setParameter(2,
	     * entity.getId().intValue()); query.executeUpdate(); TypedQuery<Thread> tquery
	     * = manager. createNamedQuery("Thread.findById", Thread.class);
	     * tquery.setParameter("id", entity.getId().intValue()); entity =
	     * tquery.getSingleResult(); manager.merge(entity);
	     */
	} catch (Exception e) {
	    e.printStackTrace();
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	manager.getTransaction().commit();
	manager.close();
	return entity;
    }

    @Override
    public Thread update(Thread entity) {
	// TODO Auto-generated method stub
	return null;
    }

}
