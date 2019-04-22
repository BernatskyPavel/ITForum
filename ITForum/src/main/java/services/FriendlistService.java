package services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Friendlist;
import model.Status;

@ApplicationScoped
public class FriendlistService
	implements IService<Friendlist>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2938614161671583076L;
    @Inject
    private DbService DbManager;

    @Override
    public List<Friendlist> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Friendlist getById(Object Id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Friendlist create(Friendlist entity) {
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	try {
	    manager.persist(entity);
	    manager.flush();
	    manager.refresh(entity);
	    entity.getUserAccount1().addFriendlists1(entity);
	    entity.getUserAccount2().addFriendlists2(entity);
	    
	    entity.getUserAccount2().getBlacklists2().removeIf(x->x.getUserAccount1().getId().intValue() == entity.getUserAccount1().getId().intValue());
	    Query query = manager.createNativeQuery("DELETE FROM public.blacklists WHERE uid = ? and buid = ?");
	    query.setParameter(1,  entity.getUserAccount2()
		    .getId().intValue());
	    query.setParameter(2, entity.getUserAccount1()
		    .getId().intValue());
	    query.executeUpdate();
	    manager.merge(entity.getUserAccount1());
	    manager.merge(entity.getUserAccount2());
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

    @Override
    public Friendlist update(Friendlist entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub

    }
}
