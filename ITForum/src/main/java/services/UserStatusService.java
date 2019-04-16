package services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.UserStatus;

@ApplicationScoped
public class UserStatusService implements IService<UserStatus> {
    @Inject
    private DbService DbManager;
    
    public static enum Statuses {
	VERIFIED (1),
	EMAIL_NOT_VERIFIED (2),
	BLOCKED (3);
	
	private final int id;
	Statuses(int id) {
	    this.id = id;
	}
	public int getId() {return id;};
    };
    
    @Override
    public List<UserStatus> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public UserStatus getById(Object Id) {
	UserStatus status = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<UserStatus> query = manager.
		      createNamedQuery("UserStatus.findById", UserStatus.class);
	query.setParameter("id", ((Integer)Id).intValue());
	try {
	    status = query.getSingleResult();
	} catch (Exception e) {
	    status = null;
	}
	manager.getTransaction().commit();
	manager.close();
	return status;
    }

    
    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public UserStatus create(UserStatus entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public UserStatus update(UserStatus entity) {
	// TODO Auto-generated method stub
	return null;
    }

}
