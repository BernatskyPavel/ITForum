package services;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

@Named("DbService")
@ApplicationScoped
public class DbService {
    private EntityManagerFactory emFactory;

    @PostConstruct
    public void init() {
	SAXReader reader = new SAXReader();
	InputStream is;
	try {
	    is = Thread.currentThread()
	    	.getContextClassLoader()
	    	.getResource("META-INF/persistence.xml").openStream();
	    Document document = reader.read(is);
		String persistence = document.getRootElement()
			.element("persistence-unit").attributeValue("name");
		emFactory = Persistence
			.createEntityManagerFactory(persistence);
	} catch (IOException | DocumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	

    }
    
    @PreDestroy
    public void destroy() {
	emFactory.close();
    }
    
    public EntityManager getManager() {
	return emFactory.createEntityManager();
    }

}
