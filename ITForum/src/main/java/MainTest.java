import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.UserAccount;

public class MainTest {

    public static void main(String[] args) {
	EntityManagerFactory emfactory = Persistence
		.createEntityManagerFactory("ITForum");

	EntityManager entitymanager = emfactory.createEntityManager();
	entitymanager.getTransaction().begin();

	TypedQuery<UserAccount> userQuery = entitymanager.createNamedQuery(
		"UserAccount.findById", UserAccount.class);
	userQuery.setParameter("id", 1);
	UserAccount result;
	result = userQuery.getSingleResult();
	entitymanager.getTransaction().commit();
	result.setAbout("TEST");
	entitymanager.getTransaction().begin();
	entitymanager.flush();
	entitymanager.getTransaction().commit();
	entitymanager.close();
	emfactory.close();

    }

}
