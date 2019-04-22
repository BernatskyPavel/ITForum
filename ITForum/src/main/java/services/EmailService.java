package services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import model.Email;
import model.Status;

@ApplicationScoped
public class EmailService implements Serializable, IService<Email> {

    /**
     * 
     */
    private static final long serialVersionUID = 4324440649135817628L;

    @Inject
    private DbService DbManager;
    @Inject
    private StatusService statusService;
    
    @Override
    public List<Email> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Email getById(Object Id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Email create(Email entity) {
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

	status.addEmail(entity);
	entity.setStatusBean(status);

	// entity.setThread(thread);

	try {
	    manager.persist(entity);
	    manager.flush();
	    manager.refresh(entity);
	    entity.getUserAccount1().addEmails1(entity);
	    entity.getUserAccount2().addEmails2(entity);
	    manager.merge(entity.getStatusBean());
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
    public Email update(Email entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub
	
    }

}
