package services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import model.Post;
import model.Status;

@ApplicationScoped
public class PostService implements IService<Post> {
    @Inject
    private DbService DbManager;
    @Inject
    private StatusService statusService;

    @Override
    public List<Post> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Post getById(Object Id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Post create(Post entity) {
	Status status = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	
	status = statusService.getById(StatusService.Statuses.APPROVED.getId());
	
	if (status == null) {
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	
	status.addPost(entity);
	entity.setStatusBean(status);
	entity.getThread().addPost(entity);
	entity.getUserAccount().addPost(entity);	
	//entity.setThread(thread);
	
	try {
	    manager.persist(entity);
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
    public Post update(Post entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub
	
    }
}
