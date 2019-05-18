package beans;

import java.io.Serializable;
import java.util.Stack;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.PrimeFaces;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import model.Category;
import services.CategoryService;

@Named
@ViewScoped
public class TestBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2842688346905334729L;
    private String postText;
    private MenuModel bread;

    @Inject
    private CategoryService service;

    public void showMessage() {
	FacesMessage message = new FacesMessage(
		FacesMessage.SEVERITY_INFO, "Test", this.postText);

	PrimeFaces.current().dialog().showMessageDynamic(message);
    }
    @PostConstruct
    public void TestBread() {
	bread = new DefaultMenuModel();
	Stack<DefaultMenuItem> tempstack = new Stack<DefaultMenuItem>();
	DefaultMenuItem item;
	Category testcat = service.getById(3);
	if (null != testcat) {
	    item = new DefaultMenuItem();
	    item.setUrl(Faces.getRequestBaseURL()
		    + "v2/category.xhtml?id="
		    + testcat.getId().intValue());
	    item.setValue(testcat.getName().length() > 20
		    ? testcat.getName().substring(0, 20)
		    : testcat.getName());
	    item.setTitle(testcat.getName());
	    tempstack.push(item);
	    while (testcat.getCategory() != null) {
		testcat = testcat.getCategory();
		item = new DefaultMenuItem();
		item.setUrl(Faces.getRequestBaseURL()
			+ "v2/category.xhtml?id="
			+ testcat.getId().intValue());
		item.setValue(testcat.getName().length() > 20
			? testcat.getName().substring(0, 20) + "..."
			: testcat.getName());
		item.setTitle(testcat.getName());
		tempstack.push(item);
	    }
	    item = new DefaultMenuItem();
	    item.setUrl(Faces.getRequestBaseURL()
		    + "v2/");
	    item.setValue("Главная");
	    item.setTitle("Главная");
	    bread.addElement(item);
	    while(!tempstack.isEmpty())
		bread.addElement(tempstack.pop());
	    
	}

    }

    /**
     * @return the postText
     */
    public String getPostText() {
	return postText;
    }

    /**
     * @param postText the postText to set
     */
    public void setPostText(String postText) {
	this.postText = postText;
    }
    /**
     * @return the bread
     */
    public MenuModel getBread() {
        return bread;
    }
    /**
     * @param bread the bread to set
     */
    public void setBread(MenuModel bread) {
        this.bread = bread;
    }
}
