package beans;

import java.io.Serializable;
import java.util.List;

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
public class CategoryBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1346475898933067165L;
    private Category currentCategory;
    private List<Category> approvedSubCategories;
    @Inject
    private CategoryService category_service;

    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext()
		.getRequest();
	if (request.getHeader("X-Requested-With") == null) {
	    currentCategory = category_service.getById(
		    Integer.parseInt(request.getParameter("id")));
	    approvedSubCategories = currentCategory
		    .getApprovedSubCategories();
	}
    }

    /**
     * @return the current_category
     */
    public Category getCurrentCategory() {
	return currentCategory;
    }

    /**
     * @param current_category the current_category to set
     */
    public void setCurrentCategory(Category currentCategory) {
	this.currentCategory = currentCategory;
    }

    /**
     * @return the approvedSubCategories
     */
    public List<Category> getApprovedSubCategories() {
	return approvedSubCategories;
    }
}
