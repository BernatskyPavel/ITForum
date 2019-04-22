package services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Blacklist;

@ApplicationScoped
public class BlacklistService
	implements IService<Blacklist>, Serializable {

    
    /**
     * 
     */
    private static final long serialVersionUID = -1346937837999219397L;
    @Inject
    private DbService DbManager;

    @Override
    public List<Blacklist> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Blacklist getById(Object Id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Blacklist create(Blacklist entity) {
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	try {
	    manager.persist(entity);
	    manager.flush();
	    manager.refresh(entity);
	    entity.getUserAccount1().addBlacklists1(entity);
	    entity.getUserAccount2().addBlacklists2(entity);
	    entity.getUserAccount2().getFriendlists2().removeIf(x->x.getUserAccount1().getId().intValue() == entity.getUserAccount1().getId().intValue());
	    Query query = manager.createNativeQuery("DELETE FROM public.friendlists WHERE uid = ? and fuid = ?");
	    query.setParameter(1, entity.getUserAccount2()
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
    public Blacklist update(Blacklist entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub
	
    }
}
