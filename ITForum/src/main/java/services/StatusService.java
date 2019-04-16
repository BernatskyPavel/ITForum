package services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Status;

@ApplicationScoped
public class StatusService implements IService<Status> {

    @Inject
    private DbService DbManager;
    
    public static enum Statuses {
	WAITING (1),
	APPROVED (2),
	REJECTED (3),
	SPAM (4),
	BLOCKED (5),
	COMPLETED (6),
	DELETED (7);
	
	private final int id;
	Statuses(int id) {
	    this.id = id;
	}
	public int getId() {return id;};
    };

    @Override
    public List<Status> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Status getById(Object Id) {
	Status status = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<Status> query = manager.
		      createNamedQuery("Status.findById", Status.class);
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
    public Status create(Status entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Status update(Status entity) {
	// TODO Auto-generated method stub
	return null;
    }

}
