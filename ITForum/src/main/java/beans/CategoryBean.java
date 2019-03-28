package beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import model.Category;
import services.CategoryService;

@Named
@ViewScoped
public class CategoryBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -1346475898933067165L;
    private Category current_category;
    @Inject
    private CategoryService category_service;
    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	current_category = category_service.getById(Integer.parseInt(request.getParameter("id")));
    }
    /**
     * @return the current_category
     */
    public Category getCurrent_category() {
        return current_category;
    }
    /**
     * @param current_category the current_category to set
     */
    public void setCurrent_category(Category current_category) {
        this.current_category = current_category;
    }
}
