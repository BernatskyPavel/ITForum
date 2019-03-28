package services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Status;

@Named("StatusService")
@ApplicationScoped
public class StatusService implements IService<Status> {

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
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void create(Status entity) {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(Status entity) {
	// TODO Auto-generated method stub

    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub

    }

}
