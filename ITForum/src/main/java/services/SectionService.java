package services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Section;

@Named("SectionService")
@ApplicationScoped
public class SectionService implements IService<Section>{
    @Inject
    private DbService DbManager;
    @Override
    public List<Section> getAll() {
	List<Section> list = new ArrayList<Section>();
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<Section> query = manager.
		      createNamedQuery("Section.findAll", Section.class);
	list = query.getResultList();
	manager.getTransaction().commit();
	manager.close();
	return list;
    }

    @Override
    public Section getById(Object Id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void create(Section entity) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void update(Section entity) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub
	
    }
}
