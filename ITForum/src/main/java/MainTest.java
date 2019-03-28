import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Section;

public class MainTest {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	EntityManagerFactory emfactory = Persistence
		.createEntityManagerFactory("ITForum");

	EntityManager entitymanager = emfactory.createEntityManager();
	entitymanager.getTransaction().begin();

	List<Section> sections = new ArrayList<Section>();
	TypedQuery<Section> query = entitymanager.
		      createNamedQuery("Section.findAll", Section.class);
	
	sections = query.getResultList();
	
	
	for(Section section : sections) {
	    System.out.println(section.getName()+"-"+section.getCategories().size());
	}
	
	entitymanager.getTransaction().commit();

	entitymanager.close();
	emfactory.close();
    }

}
